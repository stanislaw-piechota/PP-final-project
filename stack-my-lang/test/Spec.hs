import ParseJSON
import MyCodeGen
import Sprockell
import Test.Hspec

main :: IO ()
main = hspec $ do
    describe "ParseJSON.ast" $ do
        it "parses a program with declarations, arithmetic and print" $ do
            let input = "{\"program\":[{\"decl\":{\"name\":\"x\",\"type\":\"int\",\"expr\":1,\"coordinate\":{\"level\":0,\"offset\":0}}},{\"print\":{\"add\":[{\"get\":{\"name\":\"x\",\"coordinate\":{\"level\":0,\"offset\":0}}},2]}}]}"
            ast input `shouldBe`
                Program
                    [ Decl "x" "int" (Just (IntLit 1)) (Coordinate 0 0)
                    , Print (BinaryOp "add" (Get "x" (Coordinate 0 0) Nothing) (IntLit 2))
                    ]

        it "parses conditionals using the Java else object shape" $ do
            let input = "{\"program\":[{\"if\":{\"cond\":true,\"children\":[{\"print\":1}]},\"elifs\":[{\"cond\":false,\"children\":[{\"print\":2}]}],\"else\":{\"children\":[{\"print\":3}]}}]}"
            ast input `shouldBe`
                Program
                    [ If
                        (BoolLit True)
                        [Print (IntLit 1)]
                        [ElseIfBranch (BoolLit False) [Print (IntLit 2)]]
                        (Just [Print (IntLit 3)])
                    ]

        it "parses functions, calls, forks and join references" $ do
            let input = "{\"program\":[{\"function\":{\"name\":\"sum\",\"type\":\"int\",\"args\":[{\"arg\":{\"name\":\"a\",\"type\":\"int\",\"coordinate\":{\"level\":1,\"offset\":0}}},{\"arg\":{\"name\":\"b\",\"type\":\"int\",\"coordinate\":{\"level\":1,\"offset\":1}}}],\"children\":[{\"return\":{\"add\":[{\"get\":{\"name\":\"a\",\"coordinate\":{\"level\":1,\"offset\":0}}},{\"get\":{\"name\":\"b\",\"coordinate\":{\"level\":1,\"offset\":1}}}]}}]}},{\"fork\":{\"target\":\"sum\",\"args\":[{\"arg\":1},{\"arg\":2}]}},{\"join\":{\"get\":{\"name\":\"tid\",\"type\":\"int\",\"coordinate\":{\"level\":0,\"offset\":0}}}}]}"
            ast input `shouldBe`
                Program
                    [ Function
                        "sum"
                        "int"
                        [ FunctionArg "a" "int" (Coordinate 1 0)
                        , FunctionArg "b" "int" (Coordinate 1 1)
                        ]
                        [ Return
                            (BinaryOp
                                "add"
                                (Get "a" (Coordinate 1 0) Nothing)
                                (Get "b" (Coordinate 1 1) Nothing)
                            )
                        ]
                    , Fork "sum" [IntLit 1, IntLit 2]
                    , Join "tid"
                    ]

    describe "MyCodeGen.codeGen" $ do
        it "emits a terminating program with stable addresses for the provided sample" $ do
            let input = "{\"program\":[{\"decl\":{\"name\":\"var1\",\"type\":\"int\",\"expr\":null,\"coordinate\":{\"level\":0,\"offset\":0}}},{\"set\":{\"name\":\"var1\",\"expr\":-1,\"coordinate\":{\"level\":0,\"offset\":0}}},{\"decl\":{\"name\":\"var2\",\"expr\":1,\"type\":\"int\",\"coordinate\":{\"level\":0,\"offset\":2}}},{\"decl\":{\"name\":\"var3\",\"expr\":{\"decl\":{\"name\":\"var4\",\"expr\":2,\"type\":\"int\",\"coordinate\":{\"level\":0,\"offset\":4}}},\"type\":\"int\",\"coordinate\":{\"level\":0,\"offset\":6}}},{\"set\":{\"name\":\"var2\",\"expr\":{\"get\":{\"name\":\"var3\",\"coordinate\":{\"level\":0,\"offset\":6}}},\"coordinate\":{\"level\":0,\"offset\":2}}}]}"
                instructions = codeGen (ast input)
            instructions `shouldBe`
                [ Load (ImmValue (-1)) regA
                , Store regA (DirAddr 0)
                , Load (ImmValue 1) regA
                , Store regA (DirAddr 2)
                , Load (ImmValue 2) regA
                , Store regA (DirAddr 4)
                , Store regA (DirAddr 6)
                , Load (DirAddr 6) regA
                , Store regA (DirAddr 2)
                , EndProg
                ]

        it "stores an initialized declaration into its declared coordinate slot" $ do
            let input = "{\"program\":[{\"decl\":{\"name\":\"x\",\"type\":\"int\",\"expr\":1,\"coordinate\":{\"level\":0,\"offset\":4}}}]}"
            codeGen (ast input) `shouldBe`
                [ Load (ImmValue 1) regA
                , Store regA (DirAddr 4)
                , EndProg
                ]
