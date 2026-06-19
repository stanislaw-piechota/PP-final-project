module ParseJSON (Object, AST(..), parseJSON, ast) where

import Text.Parsec
import Text.Parsec.String (Parser)
import Text.Parsec.Char (anyChar)
import Text.Parsec.Combinator (many1)
import Control.Arrow (left)

data Object = IdEntry       Object  Object
            | IdObject      String
            | NumEntry      Object  Object
            | NumObject     Integer
            | BoolEntry     Object  Object
            | BoolObject    Bool
            | ObjectEntry   Object  [Object]
            | Object        [Object]
            | ArrayEntry    Object  [Object]
            deriving (Show)

num :: Parser Object
num = do
    n <- many1 digit
    return (NumObject (read n))

id' :: Parser Object
id' = do
    id'' <- char '"' *> ((:) <$> letter <*> many (letter <|> digit)) <* char '"'
    return (IdObject id'')

bool :: Parser Object
bool = do
    b <- try (string "true") <|> string "false"
    return (case b of
        "true" -> BoolObject True
        "false" -> BoolObject False)

stringEntry :: Parser Object
stringEntry = do 
    (id1, id2) <- (,) <$> (id' <* char ':') <*> (id' <* optional (char ','))
    return (IdEntry id1 id2)

numEntry :: Parser Object
numEntry = do
    (id1, val) <- (,) <$> (id' <* char ':') <*> (num <* optional (char ','))
    return (NumEntry id1 val)

boolEntry :: Parser Object
boolEntry = do
    (id1, val) <- (,) <$> (id' <* char ':') <*> (bool <* optional (char ','))
    return (BoolEntry id1 val)

entry :: Parser Object
entry = try stringEntry <|> try numEntry <|> try boolEntry <|> try objectEntry <|> arrayEntry

entity :: Parser Object
entity = try id' <|> try num <|> try bool <|> object

objectEntry :: Parser Object
objectEntry = do
    (id1, objects) <- (,) <$> (id' <* char ':') <*> (char '{' *> many entry <* char '}' <* optional (char ','))
    return (ObjectEntry id1 objects)

arrayEntry :: Parser Object
arrayEntry = do
    (id1, objects) <- (,) <$> (id' <* char ':') <*> (char '[' *> sepBy entity (char ',') <* char ']' <* optional (char ','))
    return (ArrayEntry id1 objects)

object :: Parser Object
object = do 
    o <- char '{' *> many1 entry <* char '}'
    return (Object o)

parseJSON :: String -> Either ParseError Object
parseJSON json = parse object "" json

parseJSONFile :: FilePath -> IO (Either ParseError Object)
parseJSONFile path = do
    content <- readFile path
    return $ parseJSON content

data AST = Program [AST]
        | IntLit Integer
        | Id String
        | BoolLit Bool
        | Array [AST] -- temporary node
        | Addition AST AST
        | Subtraction AST AST
        | Multiplication AST AST
        deriving (Show)

ast :: String -> AST
ast = ast' . parseJSON

ast' :: Either ParseError Object -> AST
ast' (Left err) = error $ "Parse error: " ++ show err
ast' (Right obj) = astFromJson obj

astFromJson :: Object -> AST
astFromJson (NumObject n) = IntLit n
astFromJson (IdObject s) = Id s
astFromJson (BoolObject b) = BoolLit b

astFromJson (ArrayEntry (IdObject "children") entries) = Array (map astFromJson entries)
astFromJson (Object [ObjectEntry (IdObject "program") [child]]) = Program children
    where (Array children) = astFromJson child
astFromJson (Object [ObjectEntry (IdObject "add") [child]]) = Addition left right
    where (Array [left, right]) = astFromJson child
astFromJson (Object [ObjectEntry (IdObject "sub") [child]]) = Subtraction left right
    where (Array [left, right]) = astFromJson child
astFromJson (Object [ObjectEntry (IdObject "mul") [child]]) = Multiplication left right
    where (Array [left, right]) = astFromJson child