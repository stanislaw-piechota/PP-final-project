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
sizeOfType "int" = 2
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
            "add" -> Compute Add regA regB regA
            "sub" -> Compute Sub regA regB regA
            "mul" -> Compute Mul regA regB regA
            "eq" -> Compute Equal regA regB regA
            "neq" -> Compute NEq regA regB regA
            "gt" -> Compute Gt regA regB regA
            "lt" -> Compute Lt regA regB regA
            "gte" -> Compute GtE regA regB regA
            "lte" -> Compute LtE regA regB regA
            "and" -> Compute And regA regB regA
            "or" -> Compute Or regA regB regA
            _ -> error "Unknown operator"
    in (leftInstr ++ rightInstr ++ [Pop regB, Pop regA, opInstr] ++ ([Push regA | needsPush > 0]), rightSymTable, rightFreeAddr)
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
-- ends with return value in regA, which needs to be pushed onto the stack by the caller
-- `functionCoordinate` is not properly defined right now
constructProgram (Function {functionName, functionType, functionCoordinate, functionArgs, functionChildren}) symbolTable freeAddress = 
        -- Set symbol table values (first instruction location and total size of arguments)
    let (declAddress1, newSymbolTable1, nextFreeAddress1) = getOrCreateAddress functionCoordinate "int" symbolTable freeAddress
        functionCoordinate' = functionCoordinate { offset = offset functionCoordinate + 2 }
        (declAddress2, newSymbolTable2, nextFreeAddress2) = getOrCreateAddress functionCoordinate' "int" newSymbolTable1 nextFreeAddress1
        definitionInstr = [
              Load (ImmValue 1) regA -- Load position of first instruction, from label or something
            , Store regA (DirAddr declAddress1)
            , Load (ImmValue (getFuncArgSize functionArgs)) regA
            , Store regA (DirAddr declAddress2)
            ]

        -- save args from to stack to local memory
        (argSaveInstr, newSymbolTable3, nextFreeAddress3) = saveArgsToMem functionArgs newSymbolTable2 nextFreeAddress2
        (childrenInstr, newSymbolTable4, nextFreeAddress4) = collectInstrs functionChildren newSymbolTable3 nextFreeAddress3
        bodyInstr = argSaveInstr ++ childrenInstr

        -- add a Jump instruction to the end of the function (instead of executing it at the time of definition)
        definitionInstr' = definitionInstr ++ [Jump $ Rel $ (length bodyInstr) + 1]
    in (definitionInstr' ++ bodyInstr, newSymbolTable4, nextFreeAddress4)

-- Return statements:
constructProgram (Return ast) symbolTable freeAddress = 
    let (evalInstr, newSymbolTable, nextFreeAddress) = constructProgram ast symbolTable freeAddress
        -- save return value to regA, restore stack pointer from regF, pop return address and jump
        instr = evalInstr ++ [
              Pop regA
            , Push regF
            , Pop regSP
            , Pop regB
            , Jump (Ind regB)
            ]
        in (instr, newSymbolTable, nextFreeAddress)

-- Function calls:
constructProgram (Call {callName, callType, callCoordinate, callArgs}) symbolTable freeAddress =
    let (argStackInstr, newSymbolTable, nextFreeAddress) = pushArgsToStack callArgs symbolTable freeAddress
        argSizeCoordinate = callCoordinate { offset = offset callCoordinate + 2 }
        instr = pushAllRegisters ++ [
              Load (ImmValue 99) regA -- replace with label for return address
            , Push regA
            ] ++ argStackInstr ++ [
              Push regSP
            , Pop regF
            , Load (DirAddr $ addressOfCoordinate argSizeCoordinate newSymbolTable) regA
            , Compute Sub regF regA regF
            , Load (DirAddr $ addressOfCoordinate callCoordinate newSymbolTable) regA
            , Jump (Ind regA)
            -- LABEL FOR RETURN ADDRESS
            ] ++ popAllRegisters ++ [
                Push regA
            ]
    in (instr, newSymbolTable, nextFreeAddress)

getFuncArgSize :: [FunctionArg] -> Int
getFuncArgSize [] = 0
getFuncArgSize (FunctionArg {argName, argType, argCoordinate}:args) = (sizeOfType argType) + getFuncArgSize args

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
        (insts, newSymbolTable, nextFreeAddress) = constructProgram ast symbolTable freeAddress
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
                let (newInsts, newSt, newAddr) = constructProgram ast st addr
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


codeGen :: AST -> [Instruction]
codeGen ast = instructions ++ [EndProg]
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