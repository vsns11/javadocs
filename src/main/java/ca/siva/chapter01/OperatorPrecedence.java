package ca.siva.chapter01;

import lombok.extern.slf4j.Slf4j;

/*
NOTE:
2^ 16 = 65536
SHORT -> -32,768 to 32,767  -> signed value
CHAR -> 0 to 65,535 -> unsigned value
so assigning short to char require an explicit cast and vice-versa as well since there's a data loss involved here.
1)  short is a signed 16-bit integer, whereas char is an unsigned 16-bit integer.
2) long g = 012 ; a number starts with 0 is an octal with base value represented 8 (0 to 7)
 */
@Slf4j
public class OperatorPrecedence {

    public static void main(String[] args) {
        log.info("Java Operator Precedence from highest to lowest:");
        log.info("1. Unary Postfix: expr++ expr--");
        log.info("2. Unary Prefix: ++expr --expr +expr -expr ~ !");
        log.info("3. Cast: (type) expr");
        log.info("4. Multiplicative: * / %");
        log.info("5. Additive: + -");
        log.info("6. Shift: << >> >>>");
        log.info("7. Relational: < > <= >= instanceof");
        log.info("8. Equality: == !=");
        log.info("9. Bitwise AND: &");
        log.info("10. Bitwise XOR: ^");
        log.info("11. Bitwise OR: |");
        log.info("12. Conditional AND: &&");
        log.info("13. Conditional OR: ||");
        log.info("14. Ternary: ? :");
        log.info("15. Assignment: = += -= *= /= %= &= ^= |= <<= >>= >>>=");
        log.info("16. Arrow operator: ->");
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
        log.info("Postfix: a=" + a + ", b=" + b + ", c=" + c);
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
        log.info("Unary: a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + e + ", f=" + f + ", g=" + g);
    }

    // 3. Multiplicative
    static void multiplicative() {
        int a = 2 * 3; // 6
        int b = 6 / 3; // 2
        int c = 7 % 3; // 1
        log.info("Multiplicative: a=" + a + ", b=" + b + ", c=" + c);
    }

    // 4. Additive
    static void additive() {
        int a = 2 + 3; // 5
        int b = 5 - 3; // 2
        log.info("Additive: a=" + a + ", b=" + b);
    }

    // 5. Shift
    static void shift() {
        int a = 1 << 2;  // 4
        int b = 4 >> 1;  // 2
        int c = -8 >>> 2; // 1073741822
        log.info("Shift: a=" + a + ", b=" + b + ", c=" + c);
    }

    // 6. Relational
    static void relational() {
        boolean a = 2 < 3;  // true
        boolean b = 3 > 2;  // true
        boolean c = 2 <= 2; // true
        boolean d = 3 >= 2; // true
        boolean e = "Hello" instanceof String; // true
        log.info("Relational: a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e=" + e);
    }

    // 7. Equality
    static void equality() {
        boolean a = 2 == 2; // true
        boolean b = 2 != 3; // true
        log.info("Equality: a=" + a + ", b=" + b);
    }

    // 8. Bitwise AND
    static void bitwiseAnd() {
        int a = 5 & 3; // 1 (0101 & 0011)
        log.info("Bitwise AND: a=" + a);
    }

    // 9. Bitwise XOR
    static void bitwiseXor() {
        int a = 5 ^ 3; // 6 (0101 ^ 0011)
        log.info("Bitwise XOR: a=" + a);
    }

    // 10. Bitwise OR
    static void bitwiseOr() {
        int a = 5 | 3; // 7 (0101 | 0011)
        log.info("Bitwise OR: a=" + a);
    }

    // 11. Logical AND
    static void logicalAnd() {
        boolean a = true && false; // false
        log.info("Logical AND: a=" + a);
    }

    // 12. Logical OR
    static void logicalOr() {
        boolean a = true || false; // true
        log.info("Logical OR: a=" + a);
    }

    // 13. Ternary
    static void ternary() {
        int a = (2 < 3) ? 1 : 0; // 1
        log.info("Ternary: a=" + a);
    }

    // 14. Assignment
    static void assignment() {
        int a = 1;
        a += 2; // a is 3
        a -= 1; // a is 2
        a *= 2; // a is 4
        a /= 2; // a is 2
        a %= 1; // a is 0
        log.info("Assignment: a=" + a);
    }
}
