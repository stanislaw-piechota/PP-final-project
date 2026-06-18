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
> **TODO**: ADD FUNCTION DOCS

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

> **TODO**: Add LOCK grammars

## 3. Intermediate Representation

Intermediate representation is an **AST** rewritten using **JSON** notation.
Every node has:
- name _(key - object pair)_
- intrinsic attributes _(key - value pair)_
- list of children nodes _(key - list pair)_

The schema look like the following
```json
{
  "<nodeName>": {
    "<attr1>": 1,
    "<attr2>": true,
    "children": []
  }
}
```

## 4. Feature roadmap

| Feature Name              | Frontend | Backend |
|---------------------------|----------|---------|
| Variables                 | ✅        | ❌       |
| Arithmetics op.           | ✅        | ❌       |
| Boolean op.               | ✅        | ❌       |
| Print                     | ❌        | ❌       |
| Conditionals              | ❌        | ❌       |
| While loop                | ❌        | ❌       |
| Functions (+1.0)          | ❌        | ❌       |
| Threads                   | ❌        | ❌       |
| Locks                     | ❌        | ❌       |
| Pointers (+1.0)           | ❌        | ❌       |
| Arrays (+1.0)             | ❌        | ❌       |
| Strings (+0.5)            | ❌        | ❌       |
| Soft division (+0.3)      | ❌        | ❌       |
| Call-by-reference (+0.5)  | ❌        | ❌       |
| Exception handling (+1.0) | ❌        | ❌       |

**Sum: 11.3**
