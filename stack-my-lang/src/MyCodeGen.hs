module MyCodeGen
    ( codeGen ) where

import Sprockell
import ParseJSON (AST(..))

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

codeGen :: AST -> [Instruction]
codeGen (IntLit n) = [
        Load (ImmValue $ fromInteger n) regA
    ]
codeGen (Program children) = foldl (\prev cur -> prev ++ (codeGen cur)) [] children ++ [EndProg]
codeGen (Addition left right) = l ++ r ++ [
      Compute Add regA regB regA
    , WriteInstr regA numberIO
    ]
    where l = codeGen' left regA
          r = codeGen' right regB

codeGen' :: AST -> Int -> [Instruction]
codeGen' (IntLit n) r = [
    Load (ImmValue $ fromInteger n) r
    ]