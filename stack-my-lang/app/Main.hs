module Main where

import ParseJSON (ast)
import MyCodeGen (codeGen)
import Sprockell (run, runWithDebugger, sprStates, DbgInput, debuggerSimplePrint, Instruction)
import Text.Printf (printf)
import System.Environment (getArgs)

compile :: String -> [Instruction]
compile txt = do
    let tree = ast txt
    codeGen tree

serializeProgram :: [Instruction] -> String
serializeProgram = unlines . map show

executeProgram :: FilePath -> Bool -> FilePath -> IO ()
executeProgram inputPath save outputPath = do
    txt <- readFile inputPath
    let program = compile txt
    if save 
        then writeFile outputPath (serializeProgram program) >> run [program]
        else run [program]

debug :: [[Instruction]] -> IO ()
debug program = runWithDebugger (debuggerSimplePrint myShow) program

myShow :: DbgInput -> String
myShow (instrs,s) = printf "instrs: %s\n%s\n"
                    (show instrs)
                    (unlines $ map show $ sprStates s)

main :: IO ()
main = do
    args <- getArgs
    case args of
        [input] -> executeProgram input False ""
        [input, output] -> executeProgram input True output
        _ -> putStrLn "usage: my-lang <input_path> (<output_path>)"
