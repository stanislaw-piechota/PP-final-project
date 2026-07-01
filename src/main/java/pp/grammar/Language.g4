grammar Language;

program: block;
block: statement+;
statement: ID ':' TYPE ('=' expression)? ';'    # explicitDeclaration
         | PRINT expression ';'                 # print
         | conditional                          # cond
         | expression ';'                       # expr
         | whileLoop                            # while
         | JOIN ID ';'                          # threadJoin
         | LOCK ID ';'                          # lock
         | UNLOCK ID ';'                        # unlock
         | FUNC ID LPAR (ID ':' TYPE (',' ID ':' TYPE)*)? RPAR ':' TYPE LBRACK block RBRACK # funcDef
         | RETURN expression ';'                # return;
conditional: if elseif* else?;
if: IF expression ((LBRACK block? RBRACK) | statement);
elseif: ELSEIF expression ((LBRACK block? RBRACK) | statement);
else: ELSE ((LBRACK block? RBRACK) | statement);
whileLoop: WHILE expression ((LBRACK block? RBRACK) | statement | ';');
expression: literal                                      # exprLit
          | LPAR expression RPAR                         # par
          | FORK ID (LPAR expression (',' expression)* RPAR)? # threadStart
          | ID LPAR (expression (',' expression)*)? RPAR      # funcCall
          | ID                                           # exprId
          | expression op=(TIMES | DIV) expression       # mulDiv
          | expression op=(ADD | SUB) expression         # addSub
          | expression op=(LT | GT | LE | GE) expression # comparison
          | NOT expression                               # not
          | expression op=(EQUAL | NOT_EQUAL) expression # equal
          | expression op=(AND | OR) expression          # andOr
          | ID ':=' expression              # implicitDeclaration
          | ID '=' expression               # assignment;
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
NOT: '!';

// artihmetic operators
ADD: '+';
SUB: '-';
TIMES: '*';
DIV: '/';

// grouping
LPAR: '(';
RPAR: ')';
LBRACK: '{';
RBRACK: '}';

// keywords
FUNC: 'function';
RETURN: 'return';
LOCK: 'lock';
UNLOCK: 'unlock';
FORK: 'fork';
JOIN: 'join';
WHILE: 'while';
PRINT: 'print';
IF: 'if';
ELSEIF: 'elif';
ELSE: 'else';
TYPE: 'int' | 'bool' | 'void' | 'thread' | 'func';

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