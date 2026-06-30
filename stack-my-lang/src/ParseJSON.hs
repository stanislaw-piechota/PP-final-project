module ParseJSON
    ( Object
    , Coordinate(..)
    , FunctionArg(..)
    , ElseIfBranch(..)
    , AST(..)
    , parseJSON
    , ast
    ) where

import Data.Aeson (Value(..), eitherDecode)
import qualified Data.Aeson.Key as Key
import qualified Data.Aeson.KeyMap as KeyMap
import qualified Data.ByteString.Lazy.Char8 as BL
import Data.Scientific (Scientific, floatingOrInteger)
import qualified Data.Text as Text
import qualified Data.Vector as Vector

type Object = Value
type Fields = KeyMap.KeyMap Value

data Coordinate = Coordinate
    { level :: Integer
    , offset :: Integer
    } deriving (Eq, Show)

data FunctionArg = FunctionArg
    { argName :: String
    , argType :: String
    , argCoordinate :: Coordinate
    } deriving (Eq, Show)

data ElseIfBranch = ElseIfBranch
    { elifCond :: AST
    , elifChildren :: [AST]
    } deriving (Eq, Show)

data AST
    = Program [AST]
    | Decl
        { declName :: String
        , declType :: String
        , declValue :: Maybe AST
        , declCoordinate :: Coordinate
        }
    | Set
        { setName :: String
        , setValue :: AST
        , setCoordinate :: Coordinate
        }
    | Get
        { getName :: String
        , getCoordinate :: Coordinate
        , getType :: Maybe String
        }
    | IntLit Integer
    | BoolLit Bool
    | BinaryOp
        { opName :: String
        , leftOperand :: AST
        , rightOperand :: AST
        }
    | If
        { ifCond :: AST
        , ifChildren :: [AST]
        , ifElifs :: [ElseIfBranch]
        , ifElse :: Maybe [AST]
        }
    | While
        { whileCond :: AST
        , whileChildren :: [AST]
        }
    | Print AST
    | Function
        { functionName :: String
        , functionType :: String
        , functionArgs :: [FunctionArg]
        , functionChildren :: [AST]
        , functionCoordinate :: Coordinate
        }
    | Return AST
    | Call
        { callName :: String
        , callType :: String
        , callArgs :: [AST]
        , callCoordinate :: Coordinate
        }
    | Fork
        { forkThread :: Integer
        , forkFuncCoordinate :: Coordinate
        , forkArgs :: [AST]
        }
    | Join String
    | Lock
        { lockName :: String
        , lockType :: String
        , lockCoordinate :: Coordinate
        }
    | Unlock
        { unlockName :: String
        , unlockType :: String
        , unlockCoordinate :: Coordinate
        }
    | WriteSh
        { writeAddr :: AST
        , writeValue :: AST
        }
    | ReadSh
        { readName :: String
        , readCoordinate :: Coordinate
        , readAddr :: AST
        }
    deriving (Eq, Show)

parseJSON :: String -> Object
parseJSON input =
    case eitherDecode (BL.pack input) of
        Right value -> value
        Left err -> error ("Invalid JSON: " ++ err)

ast :: String -> AST
ast input =
    case astEither input of
        Right tree -> tree
        Left err -> error err

astEither :: String -> Either String AST
astEither = parseAstValue . parseJSON

parseAstValue :: Value -> Either String AST
parseAstValue (Object obj) = parseNodeFields obj
parseAstValue (Array values)
    | Vector.length values == 1 = parseAstValue (Vector.head values)
parseAstValue (Number number) = IntLit <$> parseInteger number
parseAstValue (Bool value) = pure (BoolLit value)
parseAstValue value = Left ("Unsupported JSON node: " ++ show value)

parseNodeFields :: Fields -> Either String AST
parseNodeFields fields
    | hasKeys ["if", "elifs", "else"] fields = parseConditional fields
parseNodeFields fields =
    case KeyMap.toList fields of
        [(tag, value)] ->
            case Key.toString tag of
                "program" -> Program <$> parseStatements value
                name -> parseSingleFieldNode name value
        _ -> Left ("Unsupported node shape: " ++ show fields)

parseSingleFieldNode :: String -> Value -> Either String AST
parseSingleFieldNode "decl" value = withObject "decl" parseDecl value
parseSingleFieldNode "set" value = withObject "set" parseSet value
parseSingleFieldNode "get" value = withObject "get" parseGet value
parseSingleFieldNode "print" value = Print <$> parseAstValue value
parseSingleFieldNode "while" value = withObject "while" parseWhile value
parseSingleFieldNode "function" value = withObject "function" parseFunction value
parseSingleFieldNode "return" value = Return <$> parseAstValue value
parseSingleFieldNode "call" value = withObject "call" parseCall value
parseSingleFieldNode "fork" value = withObject "fork" parseFork value
parseSingleFieldNode "join" value = withObject "join" parseJoin value
parseSingleFieldNode "lock" value = withObject "lock" (parseLockLike Lock) value
parseSingleFieldNode "unlock" value = withObject "unlock" (parseLockLike Unlock) value
parseSingleFieldNode "writesh" value = withObject "writesh" parseWriteSh value
parseSingleFieldNode name (Array values)
    | Vector.length values == 2 =
        BinaryOp name
            <$> parseAstValue (values Vector.! 0)
            <*> parseAstValue (values Vector.! 1)
parseSingleFieldNode name value = Left ("Unsupported node shape: " ++ show [(name, value)])

parseDecl :: Fields -> Either String AST
parseDecl fields = do
    name <- requireString "name" fields
    typ <- requireString "type" fields
    coordinate <- requireCoordinate "coordinate" fields
    value <- optionalExpr fields
    pure Decl
        { declName = name
        , declType = typ
        , declValue = value
        , declCoordinate = coordinate
        }

parseSet :: Fields -> Either String AST
parseSet fields = do
    name <- requireString "name" fields
    value <- requiredExpr fields
    coordinate <- requireCoordinate "coordinate" fields
    pure Set
        { setName = name
        , setValue = value
        , setCoordinate = coordinate
        }

parseGet :: Fields -> Either String AST
parseGet fields = do
    name <- requireString "name" fields
    coordinate <- requireCoordinate "coordinate" fields
    pure Get
        { getName = name
        , getCoordinate = coordinate
        , getType = optionalString "type" fields
        }

parseConditional :: Fields -> Either String AST
parseConditional fields = do
    ifFields <- requireObjectFields "if" fields
    cond <- requireNode "cond" ifFields
    children <- requireStatements "children" ifFields
    elifs <- requireFieldAs "elifs" parseElifs fields
    elseBranch <- requireFieldAs "else" parseElseBranch fields
    pure If
        { ifCond = cond
        , ifChildren = children
        , ifElifs = elifs
        , ifElse = elseBranch
        }

parseWhile :: Fields -> Either String AST
parseWhile fields = do
    cond <- requireNode "cond" fields
    children <- requireStatements "children" fields
    pure While
        { whileCond = cond
        , whileChildren = children
        }

parseFunction :: Fields -> Either String AST
parseFunction fields = do
    name <- requireString "name" fields
    typ <- requireString "type" fields
    coordinate <- requireCoordinate "coordinate" fields
    args <- requireFieldAs "args" parseFunctionArgs fields
    children <- requireStatements "children" fields
    pure Function
        { functionName = name
        , functionType = typ
        , functionCoordinate = coordinate
        , functionArgs = args
        , functionChildren = children
        }

parseCall :: Fields -> Either String AST
parseCall fields = do
    name <- requireString "name" fields
    typ <- requireString "type" fields
    coordinate <- requireCoordinate "coordinate" fields
    args <- requireNodes "args" fields
    pure Call
        { callName = name
        , callType = typ
        , callCoordinate = coordinate
        , callArgs = args
        }

parseFork :: Fields -> Either String AST
parseFork fields = do
    thread <- requireInteger "thread" fields
    coordinate <- requireCoordinate "coordinate" fields
    args <- requireFieldAs "args" parseForkArgs fields
    pure Fork
        { forkThread = thread
        , forkFuncCoordinate = coordinate
        , forkArgs = args
        }

parseJoin :: Fields -> Either String AST
parseJoin fields =
    case lookupField "target" fields of
        Just (String str) -> pure (Join (Text.unpack str))
        Just value -> Left ("Expected string join target, got: " ++ show value)
        Nothing ->
            case lookupField "get" fields of
                Just value -> Join <$> parseJoinTarget value
                Nothing -> Left "Join node must contain either target or get"

parseJoinTarget :: Value -> Either String String
parseJoinTarget (Object obj) =
    case parseGet obj of
        Right (Get name _ _) -> pure name
        Right _ -> Left "Expected join.get to contain a variable reference"
        Left _ ->
            case parseAstValue (Object obj) of
                Right (Get name _ _) -> pure name
                Right _ -> Left "Expected join.get to contain a variable reference"
                Left err -> Left err
parseJoinTarget value =
    case parseAstValue value of
        Right (Get name _ _) -> pure name
        Right _ -> Left "Expected join.get to contain a variable reference"
        Left err -> Left err

parseLockLike :: (String -> String -> Coordinate -> AST) -> Fields -> Either String AST
parseLockLike constructor fields = do
    name <- requireString "name" fields
    typ <- requireString "type" fields
    coordinate <- requireCoordinate "coordinate" fields
    pure (constructor name typ coordinate)

parseElifs :: Value -> Either String [ElseIfBranch]
parseElifs = parseArrayWith "elifs" parseElif

parseElif :: Value -> Either String ElseIfBranch
parseElif = withObject "elif" $ \fields -> do
    cond <- requireNode "cond" fields
    children <- requireStatements "children" fields
    pure ElseIfBranch
        { elifCond = cond
        , elifChildren = children
        }

parseElseBranch :: Value -> Either String (Maybe [AST])
parseElseBranch Null = pure Nothing
parseElseBranch value = Just <$> parseElseStatements value

parseElseStatements :: Value -> Either String [AST]
parseElseStatements (Array values) = mapM parseAstValue (Vector.toList values)
parseElseStatements (Object obj) = requireStatements "children" obj
parseElseStatements value = Left ("Expected else branch array, object or null, got: " ++ show value)

parseFunctionArgs :: Value -> Either String [FunctionArg]
parseFunctionArgs = parseArrayWith "function args" parseFunctionArg

parseFunctionArg :: Value -> Either String FunctionArg
parseFunctionArg = withObject "function arg" $ \fields -> do
    let payload = unwrapArgFields fields
    name <- requireString "name" payload
    typ <- requireString "type" payload
    coordinate <- requireCoordinate "coordinate" payload
    pure FunctionArg
        { argName = name
        , argType = typ
        , argCoordinate = coordinate
        }

parseForkArgs :: Value -> Either String [AST]
parseForkArgs = parseArrayWith "fork args" parseForkArg

parseForkArg :: Value -> Either String AST
parseForkArg (Object obj) =
    case KeyMap.toList obj of
        [(tag, value)] | Key.toString tag == "arg" -> parseAstValue value
        _ -> parseNodeFields obj
parseForkArg value = parseAstValue value

parseWriteSh :: Fields -> Either String AST
parseWriteSh fields = do
    addr <- requiredExpr fields -- TODO: use proper element name
    value <- requiredExpr fields
    pure WriteSh
        { writeAddr = addr
        , writeValue = value
        }

parseReadSh :: Fields -> Either String AST
parseReadSh fields = do
    name  <- requireString "name" fields
    coordinate <- requireCoordinate "coordinates" fields
    addr <- requiredExpr fields -- this maybe needs to be changed
    pure ReadSh
        { readName = name
        , readCoordinate = coordinate
        , readAddr = addr
        }

parseStatements :: Value -> Either String [AST]
parseStatements = parseArrayWith "statement" parseAstValue

requireStatements :: String -> Fields -> Either String [AST]
requireStatements key fields = requireFieldAs key parseStatements fields

requireNodes :: String -> Fields -> Either String [AST]
requireNodes = requireStatements

requireNode :: String -> Fields -> Either String AST
requireNode key fields = requireFieldAs key parseAstValue fields

requireCoordinate :: String -> Fields -> Either String Coordinate
requireCoordinate key fields = requireFieldAs key parseCoordinate fields

optionalExpr :: Fields -> Either String (Maybe AST)
optionalExpr fields =
    optionalAliasedFieldAs ["value", "expr"] parseNullableNode fields
        >>= maybe (Left "Expected declaration to contain value or expr") pure

requiredExpr :: Fields -> Either String AST
requiredExpr fields = aliasedFieldAs ["value", "expr"] parseAstValue fields

lookupField :: String -> Fields -> Maybe Value
lookupField key = KeyMap.lookup (Key.fromString key)

requireField :: String -> Fields -> Either String Value
requireField key fields =
    case lookupField key fields of
        Just value -> pure value
        Nothing -> Left ("Missing field: " ++ key)

requireString :: String -> Fields -> Either String String
requireString key fields = requireFieldAs key parseString fields

optionalString :: String -> Fields -> Maybe String
optionalString key fields =
    case lookupField key fields of
        Just (String value) -> Just (Text.unpack value)
        _ -> Nothing

requireInteger :: String -> Fields -> Either String Integer
requireInteger key fields = requireFieldAs key parseIntegerValue fields

requireFieldAs :: String -> (Value -> Either String a) -> Fields -> Either String a
requireFieldAs key parser fields = requireField key fields >>= parser

aliasedFieldAs :: [String] -> (Value -> Either String a) -> Fields -> Either String a
aliasedFieldAs [] _ _ = Left "Expected one of the aliased fields"
aliasedFieldAs (key:keys) parser fields =
    case lookupField key fields of
        Just value -> parser value
        Nothing -> aliasedFieldAs keys parser fields

optionalAliasedFieldAs :: [String] -> (Value -> Either String a) -> Fields -> Either String (Maybe a)
optionalAliasedFieldAs [] _ _ = pure Nothing
optionalAliasedFieldAs (key:keys) parser fields =
    case lookupField key fields of
        Just value -> Just <$> parser value
        Nothing -> optionalAliasedFieldAs keys parser fields

withObject :: String -> (Fields -> Either String a) -> Value -> Either String a
withObject _ parser (Object obj) = parser obj
withObject label _ value = Left ("Expected object in field " ++ label ++ ", got: " ++ show value)

requireObjectFields :: String -> Fields -> Either String Fields
requireObjectFields key fields = requireFieldAs key (withObject key Right) fields

parseArrayWith :: String -> (Value -> Either String a) -> Value -> Either String [a]
parseArrayWith _ parser (Array values) = mapM parser (Vector.toList values)
parseArrayWith label _ value = Left ("Expected " ++ label ++ " array, got: " ++ show value)

parseCoordinate :: Value -> Either String Coordinate
parseCoordinate = withObject "coordinate" $ \fields -> do
    lvl <- requireInteger "level" fields
    off <- requireInteger "offset" fields
    pure Coordinate
        { level = lvl
        , offset = off
        }

parseNullableNode :: Value -> Either String (Maybe AST)
parseNullableNode Null = pure Nothing
parseNullableNode value = Just <$> parseAstValue value

parseString :: Value -> Either String String
parseString (String str) = pure (Text.unpack str)
parseString value = Left ("Expected string, got: " ++ show value)

parseInteger :: Scientific -> Either String Integer
parseInteger number =
    case (floatingOrInteger number :: Either Double Integer) of
        Right intValue -> pure intValue
        Left _ -> Left ("Expected integer, got: " ++ show number)

parseIntegerValue :: Value -> Either String Integer
parseIntegerValue (Number number) = parseInteger number
parseIntegerValue value = Left ("Expected integer, got: " ++ show value)

unwrapArgFields :: Fields -> Fields
unwrapArgFields fields =
    case KeyMap.toList fields of
        [(tag, Object argObj)] | Key.toString tag == "arg" -> argObj
        _ -> fields

hasKeys :: [String] -> Fields -> Bool
hasKeys keys fields = all (\key -> KeyMap.member (Key.fromString key) fields) keys
