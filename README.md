# Programming Paradigms Final Project


## 1. Useful commands
### 1.1. (Re)generate ANTLR grammar
```bash
mvn generate-sources
```

### 1.2. (Re)build project
```bash
# simplest version
mvn install

# reset cache and build
mvn clean install

# skip tests and build
mvn install -DskipTests
```

### 1.3. Run ONLY tests
```bash
mvn verify
```


## 2. Language reference
### 2.1. Types
Support for following native types:
- **int**
- **bool** (`true` / `false`)
- **void**

### 2.2. Variables
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

### 2.3. Basic logging
Standard output is managed with keyword **print**.

```
print <expr>;
print 1;
print var;
print (var1 = 2);
```

### 2.4. Conditional statements
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

### 2.5. Loop statement
```
while <expr>
    <statement>; // single statement
    
while <expr> {
    <block>
}
```

### 2.6. Functions
If function return type is not void then return statement is required, and
it must match declared return type. If function is not supposed to return a value
declare it as a type `void`.

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

### 2.7. Concurrency
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

### 2.8. Locks

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
Every node contains:
- name _(key - object pair)_
- intrinsic attributes _(key - value pair)_

Here is the api for specific instructions
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
    "children": [/* statements */ ]
  }},
  
  // function call
  {"call":  {
    "name": "<func_name>",
    "type": "<return_type>",
    "args": [ /* expressions */ ]
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

| Feature Name              | Frontend | Backend |
|---------------------------|----------|---------|
| Variables                 | ✅        | ✅       |
| Arithmetics op.           | ✅        | ✅       |
| Boolean op.               | ✅        | ✅       |
| Print                     | ✅        | ✅       |
| Conditionals              | ✅        | ✅       |
| While loop                | ✅        | ✅       |
| Functions (+1.0)          | ✅        | ❌       |
| Threads                   | ✅        | ❌       |
| Locks                     | ✅        | ❌       |
| Pointers (+1.0)           | ❌        | ❌       |
| Arrays (+1.0)             | ❌        | ❌       |
| Strings (+0.5)            | ❌        | ❌       |
| Soft division (+0.3)      | ❌        | ❌       |
| Call-by-reference (+0.5)  | ❌        | ❌       |
| Exception handling (+1.0) | ❌        | ❌       |

**Sum: 11.3**
