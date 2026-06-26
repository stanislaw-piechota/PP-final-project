module Main where

import ParseJSON (ast)
import MyCodeGen (codeGen)
import Sprockell (run, Instruction)

compile :: String -> [Instruction]
compile txt = do
    let tree = ast txt
    codeGen tree

inputPath :: FilePath
inputPath = "../src/main/resources/samples/output.json"

outputPath :: FilePath
outputPath = "generated.spril"

serializeProgram :: [Instruction] -> String
serializeProgram = unlines . map show

main :: IO ()
main = do
    print "Staring your program.."
    txt <- readFile inputPath
    let program = compile txt
    writeFile outputPath (serializeProgram program)
    run [program]
