# OCP Java SE 17 Exam Study Guide: Modules

## Table of Contents
1. [Introduction](#introduction)
2. [Modules Overview](#modules-overview)
3. [Creating a Module](#creating-a-module)
4. [Module Declarations](#module-declarations)
5. [Understanding Module Dependencies](#understanding-module-dependencies)
6. [Exporting and Opening Packages](#exporting-and-opening-packages)
7. [Service Providers and Consumers](#service-providers-and-consumers)
8. [Working with Automatic Modules](#working-with-automatic-modules)
9. [Types of Modules](#types-of-modules)
10. [Compilation and Execution](#compilation-and-execution)
11. [Module Commands](#module-commands)
12. [Advanced Module Concepts](#advanced-module-concepts)
13. [Migration Strategies](#migration-strategies)

## Introduction
This guide covers key module concepts for the OCP Java SE 17 exam. Modules improve code organization, encapsulation, and maintainability in Java applications.

## Modules Overview
Modules group packages and resources, enforce strong encapsulation, and manage dependencies. Introduced in Java 9, the module system allows you to:
- Define exposed packages.
- Specify dependencies.
- Control internal API access.

## Creating a Module
A module is defined by a `module-info.java` file located in the root of the module directory. Example:
```java
module com.example.myapp {
}
```

## Module Declarations
### `requires` Directive
Specifies module dependencies.

```shell
module <module-name> {
    requires <module-to-require>;
}
```
```java
module com.example.myapp {
    requires java.sql;
}
```

### `exports` Directive
Exports a package.
```java
module com.example.myapp {
    exports com.example.myapp.api;
}
```

### `opens` Directive
Opens a package for deep reflection.
```java
module com.example.myapp {
    opens com.example.myapp.internal;
}
```

### `provides` and `uses` Directives
Used for service providers and consumers.
```java
module com.example.myapp {
    provides com.example.myapp.service.MyService with com.example.myapp.service.impl.MyServiceImpl;
    uses com.example.myapp.service.MyService;
}
```

## Understanding Module Dependencies
### Transitive Dependencies
Makes a dependency available to other modules.
```java
module com.example.myapp {
    requires transitive java.logging;
}
```

### Static Dependencies
Used at compile time but not required at runtime.
```java
module com.example.myapp {
    requires static java.compiler;
}
```

## Exporting and Opening Packages
- **Exporting Packages**: Makes public types in a package available to other modules.
  ```java
  module com.example.myapp {
      exports com.example.myapp.api;
  }
  ```

- **Opening Packages**: Allows runtime-only access for reflection.
  ```java
  module com.example.myapp {
      opens com.example.myapp.internal to java.base;
  }
  ```

## Service Providers and Consumers
### Defining a Service Provider
Provides an implementation for a service.
```java
module com.example.myapp {
    provides com.example.myapp.service.MyService with com.example.myapp.service.impl.MyServiceImpl;
}
```

### Using a Service
Declares dependence on a service without specifying an implementation.
```java
module com.example.myapp {
    uses com.example.myapp.service.MyService;
}
```

## Working with Automatic Modules
An automatic module is created from a JAR file that does not contain a `module-info.java` file. Useful for integrating legacy libraries.
```java
module com.example.myapp {
    requires some.legacy.library;
}
```

## Types of Modules
- **Named Modules**: Explicitly defined with a `module-info.java` file.
- **Unnamed Modules**: Code on the classpath, treated as a single unnamed module.
- **Automatic Modules**: JARs on the module path without a `module-info.java` file treated as modules.

## Compilation and Execution
### Compilation Example
```sh
javac --module-path mods -d out --module-source-path src $(find src -name "*.java")
```

### Execution Example
```sh
java --module-path out -m com.example.myapp/com.example.myapp.Main
```

## Module Commands
### Listing Modules
Lists all modules known to the Java runtime.
```sh
java --list-modules
```

### Describing a Module
Describes a specific module.
```sh
java --describe-module java.base
```

### Creating a Modular JAR
Creates a modular JAR file.
```sh
jar --create --file mods/com.example.myapp.jar --main-class=com.example.myapp.Main -C out/com.example.myapp .
```

### Running a Modular JAR
Runs a modular JAR file.
```sh
java --module-path mods -m com.example.myapp
```

## Advanced Module Concepts
### Qualified Exports
Exports a package to specific modules only.
```java
module com.example.myapp {
    exports com.example.myapp.api to specific.module;
}
```

### Reflection and Modules
Allows deep reflection access.
```java
module com.example.myapp {
    opens com.example.myapp.internal to java.base;
}
```


## Migration Strategies
### 1. Automatic Modules
Quick start by using existing JAR files as modules.
```sh
java --module-path libs:mods -m com.example.myapp/com.example.myapp.Main
```

### 2. Unnamed Modules
Run existing code without immediate modularization.
```sh
javac -d out src/com/example/myapp/*.java
java -cp out com.example.myapp.Main
```

### 3. Modular JARs
Convert existing JARs into modules by adding `module-info.java`.
1. Add `module-info.java`.
2. Compile the module.
3. Create the modular JAR.
```sh
javac -d out src/com/example/myapp/*.java src/module-info.java
jar --create --file mods/com.example.myapp.jar -C out .
```

### 4. Incremental Migration
Gradually modularize large codebases.
1. Identify core components.
2. Update build and deployment process.
3. Modularize incrementally.
4. Continuously test.

### 5. Using `jdeps` Tool
Analyze dependencies in your code.
```sh
jdeps --module-path mods --check com.example.myapp.jar
```

Summary of Dependencies: Summarizes module dependencies.
```sh
jdeps -s com.example.myapp.jar
```
To view jdk internals.
```sh
jdeps -jdkinternals com.example.myapp.jar
```

### 6. Top-Down Strategy
Modularize starting from the top-level application module.
- **Pros**: Immediate benefits of modularization in application layer.
- **Cons**: Can be complex due to dependencies on non-modularized libraries.

Steps:
1. Create `module-info.java` for the top-level module.
2. Identify and resolve dependencies on other modules or libraries.
3. Modularize dependencies as needed.

### 7. Bottom-Up Strategy
Modularize starting from the low-level utility and library modules.
- **Pros**: Simplifies modularization of higher-level modules.
- **Cons**: May delay the realization of modularization benefits.

Steps:
1. Start with core utility modules and create `module-info.java` for each.
2. Gradually modularize higher-level modules that depend on the utility modules.
3. Continue until the top-level application module is modularized.
