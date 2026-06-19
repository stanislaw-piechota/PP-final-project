module Main where

import ParseJSON (ast)
import MyCodeGen (codeGen)
import Sprockell (run, Instruction)

-- Compiles a number into a spril program producing all fibonacci numbers below the number
-- Compilation might fail
compile :: String -> [Instruction]
compile txt = do
    let tree = ast txt
    codeGen tree

-- Gets a number and runs the resulting spril program of compilation succeeds
main :: IO ()
main = do
    txt <- getLine
    -- print $ compile txt
    run [compile txt]
