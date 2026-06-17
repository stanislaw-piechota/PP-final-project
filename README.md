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
- explicitly
```
uninitialisedVariableOne: int; 
uninitialisedVariableTwo: bool;
initialisedVariableOne: int = 0; 
initialisedVariableTwo: bool = true;
```
- type inference
```
inferredVariableOne := 0;
inferredVariableTwo := true;
```

## TBC...