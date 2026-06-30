{-# LANGUAGE NamedFieldPuns #-}

module MyCodeGen
    ( codeGen ) where

import Sprockell
import ParseJSON (AST(..), Coordinate(..), ElseIfBranch(..), FunctionArg(..))
import Data.HashMap (Map, insert)
import Data.Maybe
import qualified Data.HashMap as Map
import Data.Maybe (fromJust)

-- codeGen :: Integer -> [Instruction]
-- codeGen n = [ 
--          Load (ImmValue $ fromInteger n) regE  -- upper bound is hardcoded
--        , Load (ImmValue 0) regA                -- first number
--        , Load (ImmValue 1) regB                -- second number

--        -- "beginloop"
--        , Compute Gt regA regE regC             -- regA > regE ?
--        , Branch regC (Abs 12)                  -- then jump to target "end"
--        , WriteInstr regA numberIO              -- output regA
--        , Compute Add regA regB regA
--        , Compute Gt regB regE regC             -- regB > regE
--        , Branch regC (Abs 12)                  -- target "end"
--        , WriteInstr regB numberIO              -- output regB
--        , Compute Add regA regB regB
--        , Jump (Rel (-8))                       -- target "beginloop"

--        -- "end"
--        , EndProg
--        ]

type Address = Int
type SymbolTable = Map Integer Address
type VariableType = String

sizeOfType :: VariableType -> Address
sizeOfType "int" = 1
sizeOfType "bool" = 1

addressOfCoordinate :: Coordinate -> SymbolTable -> Address
addressOfCoordinate Coordinate {level, offset} symbolTable = (symbolTable Map.! level) + fromInteger offset

getOrCreateAddress :: Coordinate -> VariableType -> SymbolTable -> Address -> (Address, SymbolTable, Address)
getOrCreateAddress Coordinate {level, offset} varType symbolTable freeAddress =
    case Map.lookup level symbolTable of
        Just addr -> (addr + fromInteger offset, symbolTable, freeAddress)
        Nothing -> let newAddress = freeAddress
                       newSymbolTable = insert level newAddress symbolTable
                   in (newAddress + fromInteger offset, newSymbolTable, newAddress + sizeOfType varType)

constructStatements :: [AST] -> SymbolTable -> Address -> ([Instruction], SymbolTable, Address)
constructStatements children symbolTable freeAddress =
    foldl
        (\(prevInstr, prevSymTable, prevFreeAddr) cur ->
            let (curInstr, curSymTable, curFreeAddr) = constructProgram cur 0 prevSymTable prevFreeAddr
            in (prevInstr ++ curInstr, curSymTable, curFreeAddr)
        )
        ([], symbolTable, freeAddress)
        children

constructConditionalClauses :: [(AST, [AST])]
    -> [Instruction]
    -> SymbolTable
    -> Address
    -> ([Instruction], SymbolTable, Address)
constructConditionalClauses [] elseInstr symbolTable freeAddress = (elseInstr, symbolTable, freeAddress)
constructConditionalClauses ((condAst, children) : restClauses) elseInstr symbolTable freeAddress =
    let (condInstr, condSymTable, condFreeAddr) = constructProgram condAst 1 symbolTable freeAddress
        (childrenInstr, childrenSymTable, childrenFreeAddr) = constructStatements children condSymTable condFreeAddr
        (restInstr, restSymTable, restFreeAddr) =
            constructConditionalClauses restClauses elseInstr childrenSymTable childrenFreeAddr
        falseJumpOffset = length childrenInstr + 2
        endJumpOffset = length restInstr + 1
        instructions =
            condInstr
                ++ [ Pop regA
                   , Branch regA (Rel 2)
                   , Jump (Rel falseJumpOffset)
                   ]
                ++ childrenInstr
                ++ [Jump (Rel endJumpOffset)]
                ++ restInstr
    in (instructions, restSymTable, restFreeAddr)

softDivision :: [Instruction]
softDivision = [
        Load (ImmValue 0) regE
    ,   Load (ImmValue 1) regC
    ,   Load (ImmValue 1) regF
    ,   Compute LShift regC regF regC
    ,   Compute And regA regF regD
    ,   Compute RShift regA regF regA
    ,   Compute Add regD regC regC
    ,   Branch regA (Rel (-5))
    ,   Load (ImmValue 0) regA
    -- Cycle
    ,   Compute LShift regE regF regE
    ,   Compute And regC regF regD
    ,   Compute RShift regC regF regC
    ,   Compute Add regD regE regE
    ,   Compute GtE regE regB regD
    ,   Branch regD (Rel 2)
    ,   Jump (Rel 2)
    ,   Compute Sub regE regB regE
    ,   Compute LShift regA regF regA
    ,   Compute Add regA regD regA
    ,   Compute Xor regC regF regD
    ,   Branch regD (Rel (-11))
    ,   Push regA
    ]

constructProgram :: AST -> Int -> SymbolTable -> Address -> ([Instruction], SymbolTable, Address)
constructProgram (Program children) _ symbolTable freeAddress = constructStatements children symbolTable freeAddress
constructProgram (Decl {declName, declType, declValue, declCoordinate}) needsPush symbolTable freeAddress =
    let (declAddress, newSymbolTable, nextFreeAddress) = getOrCreateAddress declCoordinate declType symbolTable freeAddress
    in
    case declValue of
        Just val ->
            let (instructions, newerSymbolTable, newFreeAddress) = constructProgram val 1 newSymbolTable nextFreeAddress
            in (instructions ++ [
                Pop regA,
                Store regA (DirAddr declAddress)
            ] ++ ([Push regA | needsPush > 0]), newerSymbolTable, newFreeAddress)
        Nothing -> ([], newSymbolTable, nextFreeAddress)
constructProgram (Set {setName, setValue, setCoordinate}) needsPush symbolTable freeAddress =
    let (instructions, newSymbolTable, newFreeAddress) = constructProgram setValue 1 symbolTable freeAddress
    in (instructions ++ [
        Pop regA,
        Store regA (DirAddr $ addressOfCoordinate setCoordinate newSymbolTable)
    ] ++ ([Push regA | needsPush > 0]), newSymbolTable, newFreeAddress)
constructProgram (Get {getName, getCoordinate, getType}) needsPush symbolTable freeAddress =
    (Load (DirAddr $ addressOfCoordinate getCoordinate symbolTable) regA : ([Push regA | needsPush > 0]), symbolTable, freeAddress)
constructProgram (IntLit n) needsPush symbolTable freeAddress = (
    Load (ImmValue $ fromInteger n) regA : ([Push regA | needsPush > 0]),
    symbolTable, freeAddress)
constructProgram (BoolLit b) needsPush symbolTable freeAddress = (
    Load (ImmValue $ if b then 1 else 0) regA : ([Push regA | needsPush > 0]),
    symbolTable, freeAddress)
constructProgram (BinaryOp {opName, leftOperand, rightOperand}) needsPush symbolTable freeAddress =
    let (leftInstr, leftSymTable, leftFreeAddr) = constructProgram leftOperand 1 symbolTable freeAddress
        (rightInstr, rightSymTable, rightFreeAddr) = constructProgram rightOperand 1 leftSymTable leftFreeAddr
        opInstr = case opName of
            "add" -> [Compute Add regA regB regA]
            "sub" -> [Compute Sub regA regB regA]
            "mul" -> [Compute Mul regA regB regA]
            "div" -> softDivision
            "eq" -> [Compute Equal regA regB regA]
            "neq" -> [Compute NEq regA regB regA]
            "gt" -> [Compute Gt regA regB regA]
            "lt" -> [Compute Lt regA regB regA]
            "ge" -> [Compute GtE regA regB regA]
            "le" -> [Compute LtE regA regB regA]
            "and" -> [Compute And regA regB regA]
            "or" -> [Compute Or regA regB regA]
            _ -> error "Unknown operator"
    in (leftInstr ++ rightInstr ++ [Pop regB, Pop regA] ++ opInstr ++ ([Push regA | needsPush > 0]), rightSymTable, rightFreeAddr)
constructProgram (If {ifCond, ifChildren, ifElifs, ifElse}) _ symbolTable freeAddress =
    let clauses = (ifCond, ifChildren) : map (\ElseIfBranch {elifCond, elifChildren} -> (elifCond, elifChildren)) ifElifs
        elseChildren = fromMaybe [] ifElse
        (elseInstr, elseSymTable, elseFreeAddr) = constructStatements elseChildren symbolTable freeAddress
    in constructConditionalClauses clauses elseInstr elseSymTable elseFreeAddr
constructProgram (While {whileCond, whileChildren}) _ symbolTable freeAddress = 
    let (condInstr, condSymTable, condFreeAddr) = constructProgram whileCond 1 symbolTable freeAddress
        (childrenInstr, childrenSymTable, childrenFreeAddr) = constructStatements whileChildren condSymTable condFreeAddr
        endJumpOffset = length childrenInstr + 2
        loopJumpOffset = -(length condInstr + endJumpOffset + 1)
        instructions =
            condInstr
                ++ [
                    Pop regA
                    , Branch regA (Rel 2)
                    , Jump (Rel endJumpOffset)
                    ]
                ++ childrenInstr
                ++ [Jump (Rel loopJumpOffset)]
    in (instructions, symbolTable, freeAddress)
constructProgram (Print printValue) _ symbolTable freeAddress =
    let (instructions, newSymbolTable, newFreeAddress) = constructProgram printValue 1 symbolTable freeAddress
    in (instructions ++ [
        Pop regA,
        WriteInstr regA numberIO
    ], newSymbolTable, newFreeAddress)

-- Function definitions:
constructProgram (Function {functionName, functionType, functionArgs, functionChildren, functionCoordinate}) _ symbolTable freeAddress = 
        -- Set symbol table values (first instruction location and number of arguments, used for resetting the stack)
    let (declAddress1, newSymbolTable1, nextFreeAddress1) = getOrCreateAddress functionCoordinate "int" symbolTable freeAddress
        functionCoordinate' = functionCoordinate { offset = offset functionCoordinate + 2 }
        (declAddress2, newSymbolTable2, nextFreeAddress2) = getOrCreateAddress functionCoordinate' "int" newSymbolTable1 nextFreeAddress1
        definitionInstr = [
              Load (AddrLabel functionName) regA -- Load position of first instruction, from label
            , Store regA (DirAddr declAddress1)
            , Load (ImmValue (length functionArgs)) regA
            , Store regA (DirAddr declAddress2)
            ]

        -- save args from to stack to local memory
        (argSaveInstr, newSymbolTable3, nextFreeAddress3) = saveArgsToMem functionArgs newSymbolTable2 nextFreeAddress2
        (childrenInstr, newSymbolTable4, nextFreeAddress4) = collectInstrs functionChildren newSymbolTable3 nextFreeAddress3
        endLabel = functionName ++ "_end"
        
        -- if function is void, it will not have a return statement, so we need to handle that here
        returnInstr = case functionType of
            "void" -> [Push regF, Pop regSP, Jump (Ind regE)]
            _ -> []

        -- pop return address into regE, to be used later in Return
        bodyInstr = [SetLabel functionName, Pop regE] ++ argSaveInstr ++ childrenInstr ++ returnInstr ++ [SetLabel endLabel]

        -- add a Jump instruction to the end of the function (so we don't run it at the time of definition)
        definitionInstr' = definitionInstr ++ [Jump (TargetLabel endLabel)]
    in (definitionInstr' ++ bodyInstr, newSymbolTable4, nextFreeAddress4)

-- Return statements:
constructProgram (Return ast) _ symbolTable freeAddress = 
    let (evalInstr, newSymbolTable, nextFreeAddress) = constructProgram ast 1 symbolTable freeAddress
        -- save return value to regA, restore stack pointer from regF, jump to return address (saved at beginning of function)
        instr = evalInstr ++ [
              Pop regA
            , Push regF
            , Pop regSP
            , Jump (Ind regE)
            ]
        in (instr, newSymbolTable, nextFreeAddress)

-- Function calls:
constructProgram (Call {callName, callType, callArgs, callCoordinate}) needsPush symbolTable freeAddress =
    let (argStackInstr, newSymbolTable, nextFreeAddress) = pushArgsToStack callArgs symbolTable freeAddress
        argSizeCoordinate = callCoordinate { offset = offset callCoordinate + 2 }
        instr = pushAllRegisters ++ argStackInstr ++ [
              -- Save stack pointer
              Load (DirAddr $ addressOfCoordinate argSizeCoordinate newSymbolTable) regA
            , Compute Add regSP regA regF

              -- Push return address. Add 4 to get to start of popAllRegisters
            , Load (ImmValue 4) regA
            , Compute Add regPC regA regA
            , Push regA

              -- Jump to function
            , Load (DirAddr $ addressOfCoordinate callCoordinate newSymbolTable) regA
            , Jump (Ind regA)
            ] ++ popAllRegisters ++ ([Push regA | needsPush > 0])
    in (instr, newSymbolTable, nextFreeAddress)

-- Read/Write to shared memory
constructProgram (WriteSh {writeAddr, writeValue}) _ symbolTable freeAddress =
    let (valInstrs, newSymbolTable1, nextFreeAddress1) = constructProgram writeValue 1 symbolTable freeAddress
        (addrInstrs, newSymbolTable2, nextFreeAddress2) = constructProgram writeAddr 1 newSymbolTable1 nextFreeAddress1
        instructions = valInstrs ++ addrInstrs ++ [
              Pop regB
            , Pop regA
            , WriteInstr regA (IndAddr regB)
            ]
    in (instructions, newSymbolTable2, nextFreeAddress2)

constructProgram (ReadSh {readName, readCoordinate, readAddr}) needsPush symbolTable freeAddress =
    let (addrInstr, newSymbolTable, nextFreeAddress) = constructProgram readAddr 1 symbolTable freeAddress
        instructions = addrInstr ++ [
              Pop regA
            , ReadInstr (IndAddr regA)
            , Receive regA
            , Store regA (DirAddr $ addressOfCoordinate readCoordinate newSymbolTable)
            ] ++ ([Push regA | needsPush > 0])
    in (instructions, newSymbolTable, nextFreeAddress)

-- DEBUG
printRegisters :: [Instruction]
printRegisters = [
      WriteInstr regA numberIO
    , WriteInstr regB numberIO
    , WriteInstr regC numberIO
    , WriteInstr regD numberIO
    , WriteInstr regE numberIO
    , WriteInstr regF numberIO
    , WriteInstr regSP numberIO
    , WriteInstr regPC numberIO
    ]

-- Left-recursive because right-most arg is on top of the stack
saveArgsToMem :: [FunctionArg] -> SymbolTable -> Address -> ([Instruction], SymbolTable, Address)
saveArgsToMem [] st fa = ([], st, fa)
saveArgsToMem (FunctionArg {argName, argType, argCoordinate}:args) symbolTable freeAddress =
    (restInstr ++ [Pop regA, Store regA (DirAddr declAddress)], finalSt, finalFa)
    where
        (declAddress, newSymbolTable, nextFreeAddress) = getOrCreateAddress argCoordinate argType symbolTable freeAddress
        (restInstr, finalSt, finalFa) = saveArgsToMem args newSymbolTable nextFreeAddress

collectInstrs :: [AST] -> SymbolTable -> Address -> ([Instruction], SymbolTable, Address)
collectInstrs [] st fa = ([], st, fa)
collectInstrs (ast:asts) symbolTable freeAddress =
    (insts ++ restInsts, finalSymbolTable, finalFreeAddress)
    where
        (insts, newSymbolTable, nextFreeAddress) = constructProgram ast 0 symbolTable freeAddress
        (restInsts, finalSymbolTable, finalFreeAddress) = collectInstrs asts newSymbolTable nextFreeAddress

-- Ignores regA
pushAllRegisters :: [Instruction]
pushAllRegisters = [
      Push regB
    , Push regC
    , Push regD
    , Push regE
    , Push regF
    ]

-- Ignores regA
popAllRegisters :: [Instruction]
popAllRegisters = [
      Pop regF
    , Pop regE
    , Pop regD
    , Pop regC
    , Pop regB
    ]

pushArgsToStack :: [AST] -> SymbolTable -> Address -> ([Instruction], SymbolTable, Address)
pushArgsToStack asts initialSymbolTable initialFreeAddress =
    let (instructionLists, finalSymbolTable, finalFreeAddress) = 
            foldl (\(insts, st, addr) ast -> 
                let (newInsts, newSt, newAddr) = constructProgram ast 1 st addr
                in (insts ++ wrapInstructions newInsts, newSt, newAddr)
            ) ([], initialSymbolTable, initialFreeAddress) asts
    in (instructionLists, finalSymbolTable, finalFreeAddress)
  where
    -- save stack pointer into regF
    -- after evaluating arg, save it in regA, restore the stack pointer from regF, and push the final value back onto the stack from regA
    wrapInstructions insts = 
        [ Push regSP
        , Pop regF
        ]
        ++ insts ++
        [ Pop regA
        , Push regF
        , Pop regSP
        , Push regA
        ]

findLabels :: [Instruction] -> ([Instruction], [(String, Int)])
findLabels instrs = findLabels' instrs 0 [] []

-- Remaining instructions, current line number, instructions with labels removed, label table
findLabels' :: [Instruction] -> Int -> [Instruction] -> [(String, Int)] -> ([Instruction], [(String, Int)])
findLabels' [] _ labelsRemoved labels = (labelsRemoved, labels)
findLabels' (SetLabel name : rest) lineNum labelsRemoved labels = findLabels' rest lineNum labelsRemoved (labels ++ [(name, lineNum)])
findLabels' (instr:rest) lineNum labelsRemoved labels = findLabels' rest (lineNum + 1) (labelsRemoved ++ [instr]) labels

-- skips if label doesn't exist. Silent fail for now
replaceLabels :: [Instruction] -> [(String, Int)] -> [Instruction]
replaceLabels instr labels = replaceLabels' instr (Map.fromList labels) []

replaceLabels' :: [Instruction] -> Map String Int -> [Instruction] -> [Instruction]
replaceLabels' [] _ newInstr = newInstr
replaceLabels' (Load (AddrLabel name) reg : rest) labels newInstr =
    case Map.lookup name labels of
        Just addr -> replaceLabels' rest labels (newInstr ++ [Load (ImmValue addr) reg])
        Nothing -> replaceLabels' rest labels newInstr
replaceLabels' (Jump (TargetLabel name) : rest) labels newInstr =
    case Map.lookup name labels of
        Just addr -> replaceLabels' rest labels (newInstr ++ [Jump (Abs addr)])
        Nothing -> replaceLabels' rest labels newInstr
replaceLabels' (Branch reg (TargetLabel name) : rest) labels newInstr =
    case Map.lookup name labels of
        Just addr -> replaceLabels' rest labels (newInstr ++ [Branch reg (Abs addr)])
        Nothing -> replaceLabels' rest labels newInstr
replaceLabels' (instr : rest) labels newInstr = replaceLabels' rest labels (newInstr ++ [instr])

evalLabels :: [Instruction] -> [Instruction]
evalLabels instr = replaceLabels newInstr labelTable
    where (newInstr, labelTable) = findLabels instr

codeGen :: AST -> [Instruction]
codeGen ast = evalLabels instructions ++ [EndProg]
    where
        (instructions, _, _) = constructProgram ast 0 Map.empty 0

-- codeGen' :: AST -> Int -> [Instruction]
-- codeGen' (IntLit n) r = [
--     Load (ImmValue $ fromInteger n) r
--     ]

-- addr = 64 (thread state offset) + regSprID
-- set Int at addr to 0 (sleep state)
-- wait
auxThread :: [Instruction]
auxThread = [
      Load (ImmValue 64) regA
    , Compute Add regA regSprID regA
    , Load (ImmValue 0) regB
    , WriteInstr regB (IndAddr regA)
    ]