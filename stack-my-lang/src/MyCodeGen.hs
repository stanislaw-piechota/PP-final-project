{-# LANGUAGE NamedFieldPuns #-}

module MyCodeGen
    ( codeGen ) where

import Sprockell
import ParseJSON (AST(..), Coordinate(..))
import Data.HashMap (Map, insert)
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

sizeOfType :: String -> Int
sizeOfType "int" = 2

addressOfCoordinate :: Coordinate -> Int
addressOfCoordinate Coordinate {offset} = fromInteger offset

symbolKey :: String -> Coordinate -> String
symbolKey name coordinate = name ++ show coordinate

constructProgram :: AST -> Map String Int -> Int -> ([Instruction], Map String Int, Int)
constructProgram (Program children) symbolTable freeAddress =
        foldl (\(prevInstr, prevSymTable, prevFreeAddr) cur ->
                    let (curInstr, curSymTable, curFreeAddr) = constructProgram cur prevSymTable prevFreeAddr
                    in (prevInstr ++ curInstr, curSymTable, curFreeAddr)
                ) ([], symbolTable, freeAddress) children
constructProgram (Decl {declName, declType, declValue, declCoordinate}) symbolTable freeAddress =
    let declAddress = addressOfCoordinate declCoordinate
        nextFreeAddress = max freeAddress (declAddress + sizeOfType declType)
        newSymbolTable = insert (symbolKey declName declCoordinate) declAddress symbolTable
    in
    case declValue of
        Just val ->
            let (instructions, newerSymbolTable, newFreeAddress) = constructProgram val newSymbolTable nextFreeAddress
            in (instructions ++ [
                Store regA (DirAddr declAddress)
            ], newerSymbolTable, newFreeAddress)
        Nothing -> ([], newSymbolTable, nextFreeAddress)
constructProgram (Set {setName, setValue, setCoordinate}) symbolTable freeAddress =
    let (instructions, newSymbolTable, newFreeAddress) = constructProgram setValue symbolTable freeAddress
    in (instructions ++ [
        Store regA (DirAddr $ fromJust $ Map.lookup (symbolKey setName setCoordinate) newSymbolTable)
    ], newSymbolTable, newFreeAddress)
constructProgram (Get {getName, getCoordinate, getType}) symbolTable freeAddress =
    ([
        Load (DirAddr $ fromJust $ Map.lookup (symbolKey getName getCoordinate) symbolTable) regA
    ], symbolTable, freeAddress)
constructProgram (IntLit n) symbolTable freeAddress = ([
        Load (ImmValue $ fromInteger n) regA
    ], symbolTable, freeAddress)
constructProgram (Print printValue) symbolTable freeAddress =
    let (instructions, newSymbolTable, newFreeAddress) = constructProgram printValue symbolTable freeAddress
    in (instructions ++ [
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
