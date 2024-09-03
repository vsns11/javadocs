package ca.siva.chapter03;

/*
NOTE:
1. Method can have same name as that of the class. So, here Base() inside the constructor will call the actual method.
2. After calling super() inside the constructor, then it performs data assignment if any such statements exist.
So, here derived constructor calls compiler inserted super() which calls constructor of the base class.
*/

class Base {
    int id = 1000; //Line n1

    Base() {
        Base(); //Line n2
    }

    void Base() { //Line n3
        System.out.println(++id); //Line n4
    }
}

class Derived extends Base {
    int id = 2000; //Line n5

    Derived() {} //Line n6

    void Base() { //Line n7
        System.out.println(--id); //Line n8
    }
}

public class InheritanceOrderTest {
    public static void main(String[] args) {
        Base base = new Derived(); //Line n9
    }
}

