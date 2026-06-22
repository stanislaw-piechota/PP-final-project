module Main where

import ParseJSON (ast)
import MyCodeGen (codeGen)
import Sprockell (run, Instruction)

compile :: String -> [Instruction]
compile txt = do
    let tree = ast txt
    codeGen tree

outputPath :: FilePath
outputPath = "generated.spril"

serializeProgram :: [Instruction] -> String
serializeProgram = unlines . map show

main :: IO ()
main = do
    txt <- getLine
    let program = compile txt
    writeFile outputPath (serializeProgram program)
    run [program]
