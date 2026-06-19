grammar Language;

program: block;
block: statement+;
statement: ID ':' TYPE ('=' expression)? ';'    # explicitDeclaration
         | PRINT expression ';'                 # print
         | conditional                          # cond
         | expression ';'                       # expr
         | whileLoop                            # while
         | FORK ID ID (LPAR expression (',' expression)* RPAR)? ';' # threadStart
         | JOIN ID ';'                          # threadJoin;
conditional: IF expression ((LBRACK block RBRACK) | statement)
             (ELSEIF expression ((LBRACK block RBRACK) | statement))*
             (ELSE expression ((LBRACK block RBRACK) | statement))?;
whileLoop: WHILE expression ((LBRACK block RBRACK) | statement);
expression: literal                         # exprLit
          | ID ':=' expression              # implicitDeclaration
          | ID '=' expression               # assignment
          | ID                              # exprId
          | LPAR expression RPAR            # par
          | expression TIMES expression     # multiplication
          | expression ADD expression       # addition
          | expression SUB expression       # subtraction
          | expression LT expression        # lt
          | expression GT expression        # gt
          | expression LE expression        # le
          | expression GE expression        # ge
          | expression EQUAL expression     # equal
          | expression NOT_EQUAL expression # notEqual
          | expression AND expression       # and
          | expression OR expression        # or;
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
FORK: 'fork';
JOIN: 'join';
WHILE: 'while';
PRINT: 'print';
IF: 'if';
ELSEIF: 'elif';
ELSE: 'else';
TYPE: 'int' | 'bool';

// literals
BOOL: 'true' | 'false';
INTEGER: NUM+;
ID: ALPHA ALNUM*;

// comments & ws
COMMENT: '//' ~[\n]* ('\n' | EOF) -> skip;
WS: [ \t\r\n] -> skip;

fragment ALPHA: 'a'..'z' | 'A'..'Z';
fragment NUM: '0'..'9';
fragment ALNUM: ALPHA | NUM;