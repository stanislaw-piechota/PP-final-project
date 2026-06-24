{-# LANGUAGE NamedFieldPuns #-}

module MyCodeGen
    ( codeGen ) where

import Sprockell
import ParseJSON (AST(..), Coordinate(..), ElseIfBranch(..))
import Data.HashMap (Map, insert)
import qualified Data.HashMap as Map

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
            let (curInstr, curSymTable, curFreeAddr) = constructProgram cur prevSymTable prevFreeAddr
            in (prevInstr ++ curInstr, curSymTable, curFreeAddr)
        )
        ([], symbolTable, freeAddress)
        children

constructConditionalClauses
    :: [(AST, [AST])]
    -> [Instruction]
    -> SymbolTable
    -> Address
    -> ([Instruction], SymbolTable, Address)
constructConditionalClauses [] elseInstr symbolTable freeAddress = (elseInstr, symbolTable, freeAddress)
constructConditionalClauses ((condAst, children) : restClauses) elseInstr symbolTable freeAddress =
    let (condInstr, condSymTable, condFreeAddr) = constructProgram condAst symbolTable freeAddress
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

constructProgram :: AST -> SymbolTable -> Address -> ([Instruction], SymbolTable, Address)
constructProgram (Program children) symbolTable freeAddress =
    constructStatements children symbolTable freeAddress
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
constructProgram (If {ifCond, ifChildren, ifElifs, ifElse}) symbolTable freeAddress =
    let clauses = (ifCond, ifChildren) : map (\ElseIfBranch {elifCond, elifChildren} -> (elifCond, elifChildren)) ifElifs
        elseChildren = maybe [] id ifElse
        (elseInstr, elseSymTable, elseFreeAddr) = constructStatements elseChildren symbolTable freeAddress
    in constructConditionalClauses clauses elseInstr elseSymTable elseFreeAddr
constructProgram (Print printValue) symbolTable freeAddress =
    let (instructions, newSymbolTable, newFreeAddress) = constructProgram printValue symbolTable freeAddress
    in (instructions ++ [
        Pop regA,
        WriteInstr regA numberIO
    ], newSymbolTable, newFreeAddress)

codeGen :: AST -> [Instruction]
codeGen ast = instructions ++ [EndProg]
    where
        (instructions, _, _) = constructProgram ast Map.empty 0

-- codeGen' :: AST -> Int -> [Instruction]
-- codeGen' (IntLit n) r = [
--     Load (ImmValue $ fromInteger n) r
--     ]
