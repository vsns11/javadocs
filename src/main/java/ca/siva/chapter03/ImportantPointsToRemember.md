# Important Points on Java Records

A **record declaration** specifies a new record class, a restricted kind of class that defines a simple aggregate of values. A record declaration implicitly creates instance fields, which are private and final. It also implicitly creates public accessor methods for these fields. Together, the fields and the corresponding methods are called **record components**.

An accessor method is a method with the same name as the record field and an empty formal parameter list. Such methods act as the getter methods for those fields. For example:

```java
public record Student(
  int id,   // record component
  String name  // record component
) // record header
{ } // record body
```
This is roughly equivalent to the following class:

```java
public final class Student extends Record {
   private final int id;    // component field
   private final String name;   // component field
   
   public Student(int id, String name) {   // canonical constructor
      this.id = id;
      this.name = name;
   }
   
   public int id() { return id; }   // accessor
   public String name() { return name; }   // accessor
   
   // hashCode, equals, and toString methods are also provided by the compiler.
}
```
Observe that the accessor methods do not follow the JavaBeans convention (where a getter method is prefixed with get). Further observe that instance fields are final and the class does not have any setters.

Basic Rules:

1. You can define a top-level record (i.e., directly under a package), a nested member record class (i.e., within a class/interface), or a local record (i.e., within a method).
2. A nested record class is implicitly static. That is, every member record class and local record class is static. It is permitted for the declaration of a member record class to redundantly specify the static modifier, but it is not permitted for the declaration of a local record class.
A record class is implicitly final. It is permitted for the declaration of a record class to redundantly specify the final modifier.
3. Records cannot be marked abstract, sealed, or non-sealed.
4. The direct superclass type of a record class is Record. Thus, a record cannot have an extends clause and so it cannot extend any other class.
5. The serialization mechanism treats instances of a record class differently than ordinary serializable or externalizable objects. In particular, a record object is deserialized using the canonical constructor.
Restrictions on Record Declarations
6. The body of a record declaration may contain constructors and member declarations, as well as static initializers. But there are some restrictions:

7. It cannot contain an instance field declaration (static fields are allowed).
8. It cannot have an instance initializer (static initializers are allowed).
9. It cannot have abstract or native methods.

### Constructors and Methods in Records
1. If a canonical constructor is not provided by the programmer explicitly, the compiler will provide one automatically with the same access modifier as that of the record itself. 
2. It takes the same arguments as the record components and initializes all the components. 
3. This is called the canonical constructor. (See the example above.)

A programmer may provide the canonical constructor in regular form (just like the way you define a constructor for any regular class) or in a compact form like this:

```java
public record Student(int id, String name) {
    public Student {   // no parameter list is specified here
       if (id < 0) throw new IllegalArgumentException();
    }
}
```
After the last line of the compact constructor, all component fields of the record class are implicitly initialized to the values of the corresponding formal parameters specified in the call to new.

If you write a non-canonical constructor in a record explicitly, then, on the first line of such a constructor, you must provide a call to either the canonical constructor or another constructor. For example:

```java

public record Student(int id, String name) {
    public Student() {   // a non-canonical constructor
        this(10);   // this line or a call to the canonical constructor is required 
    }
    public Student(int id) {   // another non-canonical constructor
        this(id, "");   // this line is required 
    }
    public Student(int id, String name) {   // regular form canonical constructor
        this.id = id;
        this.name = name;
    }
}
```
An important difference between a record and a regular class with respect to constructors is that the compiler provides a canonical constructor for a record even when the programmer provides a non-canonical constructor and does not provide the canonical constructor. In a regular class, the compiler does not provide the default no-args constructor if the programmer provides any other constructor for the class.

A canonical constructor cannot be generic.

A canonical constructor cannot have a throws clause (not even a throws clause with unchecked exceptions is allowed), but a non-canonical constructor may have a throws clause.

Accessor methods must not throw any exceptions.

You may have other methods in a record as needed. In this respect, a record is like any other class.

It is a compile-time error for a record declaration to declare a record component with the name clone, finalize, getClass, hashCode, notify, notifyAll, toString, or wait. Observe that all these are public or protected methods of Object class.

