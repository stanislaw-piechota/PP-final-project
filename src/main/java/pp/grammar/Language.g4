grammar Language;

block: statement+;
statement: ID ':' TYPE ('=' expression)? ';'    # explicit_declaration
         | ID ':=' expression ';'               # implicit_declaration
         | PRINT expression ';'                 # print
         | conditional                          # cond
         | expression ';'                       # expr;
conditional: IF expression ((LBRACK block RBRACK) | statement)
             (ELSEIF expression ((LBRACK block RBRACK) | statement))*
             (ELSE expression ((LBRACK block RBRACK) | statement))?;
expression: literal                         # exprLit
          | ID                              # exprId
          | binaryOp                        # exprBinOp
          | ID '=' expression               # assignment;
binaryOp: binaryOp TIMES binaryOp           # multiplication
        | binaryOp ADD binaryOp             # addition
        | binaryOp SUB binaryOp             # substraction
        | binaryOp AND binaryOp             # and
        | binaryOp OR binaryOp              # or
        | binaryOp EQUAL binaryOp           # equal
        | binaryOp NOT_EQUAL binaryOp       # notEqual
        | binaryOp LT binaryOp              # lt
        | binaryOp GT binaryOp              # gt
        | binaryOp LE binaryOp              # le
        | binaryOp GE binaryOp              # ge
        | LPAR binaryOp RPAR                # par
        | literal                           # binOpLit
        | ID                                # binOpId;
literal: int | bool;
bool: BOOL;
int: SUB? INTEGER;

// logical operators
EQUAL: '==';
NOT_EQUAL: '!=';
GT: '>';
LT: '<';
GE: '>=';
LE: '<=';
AND: '&&';
OR: '||';

// artihmetic operators
ADD: '+';
SUB: '-';
TIMES: '*';

// grouping
LPAR: '(';
RPAR: ')';
LBRACK: '{';
RBRACK: '}';

// keywords
PRINT: 'print';
IF: 'if';
ELSEIF: 'elif';
ELSE: 'else';
TYPE: 'int' | 'bool';

// literals
BOOL: 'true' | 'false';
INTEGER: NUM+;
ID: ALPHA ALNUM+;

// comments & ws
COMMENT: '//' ~[\n]* '\n' -> skip;
WS: [ \t\r\n] -> skip;

fragment ALPHA: 'a'..'z' | 'A'..'Z';
fragment NUM: '0'..'9';
fragment ALNUM: ALPHA | NUM;