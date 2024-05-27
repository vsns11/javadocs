package ca.siva.chapter01;

public class OperatorPrecedence {

    public static void main(String[] args) {
        System.out.println("Java Operator Precedence from highest to lowest:");
        System.out.println("1. Unary Postfix: expr++ expr--");
        System.out.println("2. Unary Prefix: ++expr --expr +expr -expr ~ !");
        System.out.println("3. Cast: (type) expr");
        System.out.println("4. Multiplicative: * / %");
        System.out.println("5. Additive: + -");
        System.out.println("6. Shift: << >> >>>");
        System.out.println("7. Relational: < > <= >= instanceof");
        System.out.println("8. Equality: == !=");
        System.out.println("9. Bitwise AND: &");
        System.out.println("10. Bitwise XOR: ^");
        System.out.println("11. Bitwise OR: |");
        System.out.println("12. Conditional AND: &&");
        System.out.println("13. Conditional OR: ||");
        System.out.println("14. Ternary: ? :");
        System.out.println("15. Assignment: = += -= *= /= %= &= ^= |= <<= >>= >>>=");
        System.out.println("16. Arrow operator: ->");
        postfix();
        unary();
        multiplicative();
        additive();
        shift();
        relational();
        equality();
        bitwiseAnd();
        bitwiseXor();
        bitwiseOr();
        logicalAnd();
        logicalOr();
        ternary();
        assignment();

    }

    // 1. Postfix
    static void postfix() {
        int a = 1;
        int b = a++; // b is 1, a is 2
        int c = a--; // c is 2, a is 1
        System.out.println("Postfix: a=" + a + ", b=" + b + ", c=" + c);
    }

    // 2. Unary
    static void unary() {
        int a = 1;
        int b = ++a; // b is 2, a is 2
        int c = --a; // c is 1, a is 1
        int d = +a;  // d is 1
        int e = -a;  // e is -1
        boolean f = true;
        boolean g = !f; // g is false
        System.out.println("Unary: a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + e + ", f=" + f + ", g=" + g);
    }

    // 3. Multiplicative
    static void multiplicative() {
        int a = 2 * 3; // 6
        int b = 6 / 3; // 2
        int c = 7 % 3; // 1
        System.out.println("Multiplicative: a=" + a + ", b=" + b + ", c=" + c);
    }

    // 4. Additive
    static void additive() {
        int a = 2 + 3; // 5
        int b = 5 - 3; // 2
        System.out.println("Additive: a=" + a + ", b=" + b);
    }

    // 5. Shift
    static void shift() {
        int a = 1 << 2;  // 4
        int b = 4 >> 1;  // 2
        int c = -8 >>> 2; // 1073741822
        System.out.println("Shift: a=" + a + ", b=" + b + ", c=" + c);
    }

    // 6. Relational
    static void relational() {
        boolean a = 2 < 3;  // true
        boolean b = 3 > 2;  // true
        boolean c = 2 <= 2; // true
        boolean d = 3 >= 2; // true
        boolean e = "Hello" instanceof String; // true
        System.out.println("Relational: a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + e);
    }

    // 7. Equality
    static void equality() {
        boolean a = 2 == 2; // true
        boolean b = 2 != 3; // true
        System.out.println("Equality: a=" + a + ", b=" + b);
    }

    // 8. Bitwise AND
    static void bitwiseAnd() {
        int a = 5 & 3; // 1 (0101 & 0011)
        System.out.println("Bitwise AND: a=" + a);
    }

    // 9. Bitwise XOR
    static void bitwiseXor() {
        int a = 5 ^ 3; // 6 (0101 ^ 0011)
        System.out.println("Bitwise XOR: a=" + a);
    }

    // 10. Bitwise OR
    static void bitwiseOr() {
        int a = 5 | 3; // 7 (0101 | 0011)
        System.out.println("Bitwise OR: a=" + a);
    }

    // 11. Logical AND
    static void logicalAnd() {
        boolean a = true && false; // false
        System.out.println("Logical AND: a=" + a);
    }

    // 12. Logical OR
    static void logicalOr() {
        boolean a = true || false; // true
        System.out.println("Logical OR: a=" + a);
    }

    // 13. Ternary
    static void ternary() {
        int a = (2 < 3) ? 1 : 0; // 1
        System.out.println("Ternary: a=" + a);
    }

    // 14. Assignment
    static void assignment() {
        int a = 1;
        a += 2; // a is 3
        a -= 1; // a is 2
        a *= 2; // a is 4
        a /= 2; // a is 2
        a %= 1; // a is 0
        System.out.println("Assignment: a=" + a);
    }
}
