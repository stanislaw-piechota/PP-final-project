{-# LANGUAGE NamedFieldPuns #-}

module MyCodeGen
    ( codeGen ) where

import Sprockell
import ParseJSON (AST(..), Coordinate(..), FunctionArg(..))
import Data.HashMap (Map, insert)
import qualified Data.HashMap as Map
import Data.Maybe (fromJust)
import Debug.Trace (trace)

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

constructProgram :: AST -> SymbolTable -> Address -> ([Instruction], SymbolTable, Address)
constructProgram (Program children) symbolTable freeAddress =
        foldl (\(prevInstr, prevSymTable, prevFreeAddr) cur ->
                    let (curInstr, curSymTable, curFreeAddr) = constructProgram cur prevSymTable prevFreeAddr
                    in (prevInstr ++ curInstr, curSymTable, curFreeAddr)
                ) ([], symbolTable, freeAddress) children
constructProgram (Decl {declName, declType, declValue, declCoordinate}) symbolTable freeAddress =
    let (declAddress, newSymbolTable, nextFreeAddress) = getOrCreateAddress declCoordinate declType symbolTable freeAddress
    in
    case declValue of
        Just val ->
            let (instructions, newerSymbolTable, newFreeAddress) = constructProgram val newSymbolTable nextFreeAddress
            in (instructions ++ [
                Pop regA,
                Store regA (DirAddr declAddress),
                Push regA
            ], newerSymbolTable, newFreeAddress)
        Nothing -> ([], newSymbolTable, nextFreeAddress)
constructProgram (Set {setName, setValue, setCoordinate}) symbolTable freeAddress =
    let (instructions, newSymbolTable, newFreeAddress) = constructProgram setValue symbolTable freeAddress
    in (instructions ++ [
        Pop regA,
        Store regA (DirAddr $ addressOfCoordinate setCoordinate newSymbolTable)
    ], newSymbolTable, newFreeAddress)
constructProgram (Get {getName, getCoordinate, getType}) symbolTable freeAddress =
    ([
        Load (DirAddr $ addressOfCoordinate getCoordinate symbolTable) regA,
        Push regA
    ], symbolTable, freeAddress)
constructProgram (IntLit n) symbolTable freeAddress = ([
        Load (ImmValue $ fromInteger n) regA,
        Push regA
    ], symbolTable, freeAddress)
constructProgram (BoolLit b) symbolTable freeAddress = ([
        Load (ImmValue $ if b then 1 else 0) regA,
        Push regA
    ], symbolTable, freeAddress)
constructProgram (BinaryOp {opName, leftOperand, rightOperand}) symbolTable freeAddress =
    let (leftInstr, leftSymTable, leftFreeAddr) = constructProgram leftOperand symbolTable freeAddress
        (rightInstr, rightSymTable, rightFreeAddr) = constructProgram rightOperand leftSymTable leftFreeAddr
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
    in (leftInstr ++ rightInstr ++ [Pop regB, Pop regA, opInstr, Push regA], rightSymTable, rightFreeAddr)
constructProgram (Print printValue) symbolTable freeAddress =
    let (instructions, newSymbolTable, newFreeAddress) = constructProgram printValue symbolTable freeAddress
    in (instructions ++ [
        WriteInstr regA numberIO
    ], newSymbolTable, newFreeAddress)

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

pushAllRegisters :: [Instruction]
pushAllRegisters = [
      Push regA
    , Push regB
    , Push regC
    , Push regD
    , Push regE
    , Push regF
    ]

popAllRegisters :: [Instruction]
popAllRegisters = [
      Pop regF
    , Pop regE
    , Pop regD
    , Pop regC
    , Pop regB
    , Pop regA
    ]

pushArgsToStack :: [AST] -> SymbolTable -> Address -> ([Instruction], SymbolTable, Address)
pushArgsToStack asts initialSymbolTable initialFreeAddress =
    let (instructionLists, finalSymbolTable, finalFreeAddress) = 
            foldl (\(insts, st, addr) ast -> 
                let (newInsts, newSt, newAddr) = constructProgram ast st addr
                in (insts ++ newInsts, newSt, newAddr)
            ) ([], initialSymbolTable, initialFreeAddress) asts
    in (wrapInstructions instructionLists, finalSymbolTable, finalFreeAddress)
  where
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
        (instructions, _, _) = constructProgram ast Map.empty 0

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