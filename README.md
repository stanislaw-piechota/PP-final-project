# Programming Paradigms Final Project

## 1. Commands
### 1.1. Prerequisites
- Java 21+ ([Installation guide](https://www.java.com/en/download/help/download_options.html))
- Maven 3.8.7+ ([Installation guide](https://maven.apache.org/install.html))
- Stack 3.7.1+ ([Installation guide](https://docs.haskellstack.org/en/stable/install_and_upgrade/))
- GHCi 9.6.5+ ([Installation guide](https://www.haskell.org/ghc/download.html))

**WARNING**: Make sure that each tool's binaries are in _PATH_.

### 1.2. Installation

#### 1.2.1. Linux
```bash
sudo chmod +x compile_executables langC

./compile_executables

./langC -h
```

#### 1.2.2. Windows
```bash
# run in cmd
compile_executables.bat

langC.bat -h
```

If after these commands a help message is printed, then the installation was successful
and the compiler is ready to use.


### 1.3. Running the compiler
**WARNING**: Use relative path to the compiler wrapper (`langC` or `langC.bat`) as the installation doesn't add the 
binary to _PATH_.

```bash
# Compile and run, no IR, no SPRIL
./langC input.lang

# Compile and run, save IR and SPRIL
# For output parameter it is recommended to not provide an extension
./langC input.lang -o output

# Only compile and save IR and SPRIL
./langC input.lang --no-run -o output

# Compile and run display verbose output
./langC input.lang -v

# Compile and run using different backend binary
./langC input.lang --backend ./myBackendBinary

# Display help message
./langC -h
```

For Windows use `langC.bat` instead of `./langC`.


### 1.4. (Re)generate ANTLR grammar
```bash
mvn generate-sources
```

### 1.5. (Re)build project
```bash
# test and build
mvn install

# reset cache, test and build
mvn clean install

# skip tests and build
mvn install -DskipTests
```

### 1.6. Run ONLY tests
```bash
mvn verify
```


## 2. Language reference
### 2.1. Types
Support for following native types:
- **int**
- **bool** (`true` / `false`)
- **void**

### 2.2a. Variables
Variable name _should_ follow camel case convention. 
The identifier consists of:
- at least **1** letter at the beginning
- combination of alphanumeric characters

Variable type can be declared:
- explicit declaration with assignment
```
variableOne: int = 0; 
variableTwo: bool = true;
```
- explicit declaration without assignment
```
variableOne: int; 
variableTwo: bool;
```
_**WARNING**: Usage of uninitialised variable will cause an error._
- type inference
```
inferredVariableOne := 0;
inferredVariableTwo := true;
```

It is possible to redeclare a variable as long as it is in 
different scope (block), otherwise it will cause an error.

It is possible to use **implicit** declaration as an expression.


### 2.3. Assignment
You can assign a value only to a declared identifier, otherwise it gives a compilation error.
The newly assigned expression must match declared variable type, otherwise it gives an error.

```
<identifier> = <new_expression>;
```

It is possible to use assignment as en expression, ex.
```
var2: int = (var1 = var1 + 1); 
```


### 2.4. Arithmetic and boolean operations
Each operation has predefined restrictions regarding argument types and the 
resulting type. The following operations are allowed (with corresponding type restrictions)

| Operation          | Symbol   | Arguments  | Result |
|--------------------|----------|------------|--------|
| Addition           | **+**    | int, int   | int    |
| Subtraction        | **-**    | int, int   | int    |
| Multiplication     | **_*_**  | int, int   | int    |
| Not                | **!**    | bool       | bool   |
| And                | **&&**   | bool, bool | bool   |
| Or                 | **\|\|** | bool, bool | bool   |
| Equal              | **==**   | bool, bool | bool   |
|                    |          | int, int   | bool   |
| Non-equal          | **!=**   | bool, bool | bool   |
|                    |          | int, int   | bool   |
| Lower than         | **<**    | int, int   | bool   |
| Greater than       | **>**    | int, int   | bool   |
| Lower/equal than   | **<=**   | int, int   | bool   |
| Greater/equal than | **>=**   | int, int   | bool   |


### 2.5. Basic logging
Standard output is managed with keyword **print**.

```
print <expr>;
print 1;
print var;
print (var1 = 2);
```

### 2.6. Conditional statements
Conditional statements are using keywords 
**if**, **elif** and **else**. 
If the block contains only one line brackets can be omitted.
```
if <expr>
    <statement>; // single statement

// block statement    
if <expr> {
    <statement1>;
    <statemtnt2>;
}

if <expr> {
    <block>
} elif <expr> {
    <block>
} 

// There can be more elif blocks

else {
    <block>
}
```


### 2.7. Loop statement
```
while <expr>
    <statement>; // single statement
    
while <expr> {
    <block>
}
```


### 2.8. Functions
If function return type is not void then return statement is required, and
it must match declared return type. If function is not supposed to return a value
declare it as a type `void`. Empty return statements are **not allowed** yet.

You can create nested function. Inside the inner scope of a function it is possible
to shadow variable names. Function declared in inner scope of a function are only 
accessible in this scope or in children scopes.

Call must be performed on a function - otherwise it produces an error.
Argument must match exactly the number of declared arguments and the types
must be matching as well.

```
// function definition
function <function_id>(<arg_name>: <arg_type>[, ...]): <return_type> {
    // function body
    
    return <expr>;
}

// example of nested function
function f(x: int): void {
    function g(x: int): int {
        return x;
    }
    
    print g(x);
}

// function call
<function_id>(<expr>[, ...])

// example call
f(1);
```


### 2.9. Concurrency
To deal with concurrency we use **fork**/**join** convention.
Thread target is a function, and it is possible to add 
parameters in a comma separated list of expressions.
Thread ID is **managed by the programmer** and the convention
is the same as variable identifier.
```
fork <threadId> <targetFunc>; // no parameters thread
fork <threadId> <targetFunc> (<expr>[, <expr>]) // passing parameters

join <threadId>;
```


### 2.10. Locks

To lock and unlock a resource a corresponding identifier is required.

```
lock <resource_id>;
unlock <resource_id>;

// that is wrong
lock 1;
unlock true;
```


## 3. Intermediate Representation

Intermediate representation is an **AST** rewritten using **JSON** notation.

```json5
[
  // program (as a whole)
  {"program":  [/* statements */]},
  
  // declaration
  {"decl": {
    "name": "<name>",
    "type": "<type>",
    "expr": "<expression_obj>",
    "coordinate": {
      "level": 0, // <SCT level>
      "offset": 0 // <SCT level offset>
    }
  }},
  
  // assignment 
  {"set":  {
    "name": "<name>",
    "expr": "<expression_obj>",
    "coordinate": {
      "level": 0, // <SCT level>
      "offset": 0 // <SCT level offset>
    }
  }},
  
  // reference
  {"get":  {
    "name": "<name>",
    "coordinate": {
      "level": 0, // <SCT level>
      "offset": 0 // <SCT level offset>
    }
  }},
  
  // boolean & arithmetic expressions
  {"<operation>": [
    /* expressions */
  ]},
  
  // conditional
  {
    "if": {
      "cond": "<expression>",
      "children": [ /* statements */ ]
    },
    "elifs": [ // may be empty
      {
        "cond":  "<expression>",
        "children": [ /* statements */ ]
      }
    ],
    "else": {
      "children": [ /* statements */ ]
    } // if no else present => null
  },
  
  // while loop
  {"while":  {
    "cond": "<expression>",
    "children": [ /* statements */ ]
  }},
  
  // print
  {"print":  "<expression>"},
  
  // function definitions
  {"function":  {
    "name": "<func_name>",
    "type": "<return_type>",
    "args": [
      {
        "name": "<arg_name>",
        "type": "<type>",
        "coordinate": {
          "level": 0, // <SCT level>
          "offset": 0 // <SCT level offset>
        }
      }
    ],
    "children": [/* statements */ ],
    "coordinate": {
      "level": 0, // <SCT level>
      "offset": 0 // <SCT offset> (function def takes 4 bytes of memory)
    }
  }},
  
  // function call
  {"call":  {
    "name": "<func_name>",
    "type": "<return_type>",
    "args": [ /* expressions */ ],
    "coordinate": {
      "level": 0, // <SCT level>
      "offset": 0 // <SCT offset>
    }
  }},
  
  // fork (thread start)
  {"fork": {
    "target": "<func_name>",
    "args": [ /* expressions */ ]
  }},
  
  // join (thread end)
  {"join":  "<thread_id>"}, // of int type,
  
  // lock
  {"lock":  {
    "name": "<var_name>",
    "type": "<type>",
    "coordinate": {
      "level": 0,
      "offset": 0
    }
  }},

  // unlock
  {"unlock":  {
    "name": "<var_name>",
    "type": "<type>",
    "coordinate": {
      "level": 0,
      "offset": 0
    }
  }}
]
```

IR has **no whitespaces** at all - single line.

## 4. Feature roadmap

|  Feature Name             | Frontend | Backend |
|---------------------------|----------|---------|
| Variables                 | ✅        | ✅       |
| Arithmetics op.           | ✅        | ✅       |
| Boolean op.               | ✅        | ✅       |
| Print                     | ✅        | ✅       |
| Conditionals              | ✅        | ✅       |
| While loop                | ✅        | ✅       |
| Functions (+1.0)          | ✅        | ✅       |
| Threads                   | ✅        | ❌       |
| Locks                     | ✅        | ❌       |
| Pointers (+1.0)           | ❌        | ❌       |
| Arrays (+1.0)             | ❌        | ❌       |
| Strings (+0.5)            | ❌        | ❌       |
| Soft division (+0.3)      | ❌        | ✅       |
| Call-by-reference (+0.5)  | ❌        | ❌       |
| Exception handling (+1.0) | ❌        | ❌       |

**Sum: 11.3**
