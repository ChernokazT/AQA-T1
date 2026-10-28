package org.example;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

public class SecondLectureTest {

    private static final Random RANDOM = new Random();

    @Test
    void testIsEven() {
        System.out.println("========================Test method start");

        int n = RANDOM.nextInt(100) + 1;
        boolean actual = isEven(n);
        boolean expected = (n % 2 == 0);

        System.out.println("isEven(" + n + ") = " + actual + ", ожидалось: " + expected);

        if (actual == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }

    @RepeatedTest(5)
    void testIsEvenRepeated() {
        System.out.println("========================Test method start");

        int n = RANDOM.nextInt(100) + 1;
        boolean actual = isEven(n);
        boolean expected = (n % 2 == 0);

        System.out.println("isEven(" + n + ") = " + actual + ", ожидалось: " + expected);

        if (actual == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }

    @ParameterizedTest(name = "isEven({0})")
    @CsvSource({
            "2, true",
            "3, false",
            "100, true",
            "57, false"
    })
    void testIsEvenParameterized(int n, boolean expected) {
        System.out.println("========================Test method start");

        boolean actual = isEven(n);
        System.out.println("isEven(" + n + ") = " + actual + ", ожидалось: " + expected);

        if (actual == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }


    @Test
    void testCheckAccess() {
        System.out.println("========================Test method start");

        int age = RANDOM.nextInt(100);
        String actual = checkAccess(age);
        String expected = (age >= 18) ? "Access granted" : "Access denied";

        System.out.println("checkAccess(" + age + ") = " + actual + ", ожидалось: " + expected);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }

    @RepeatedTest(5)
    void testCheckAccessRepeated() {
        System.out.println("========================Test method start");

        int age = RANDOM.nextInt(100);
        String actual = checkAccess(age);
        String expected = (age >= 18) ? "Access granted" : "Access denied";

        System.out.println("checkAccess(" + age + ") = " + actual + ", ожидалось: " + expected);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }

    @ParameterizedTest(name = "checkAccess({0})")
    @CsvSource({
            "0, Access denied",
            "17, Access denied",
            "18, Access granted",
            "99, Access granted"
    })
    void testCheckAccessParameterized(int age, String expected) {
        System.out.println("========================Test method start");

        String actual = checkAccess(age);
        System.out.println("checkAccess(" + age + ") = " + actual + ", ожидалось: " + expected);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }


    @Test
    void testGetGrade() {
        System.out.println("========================Test method start");

        int score = RANDOM.nextInt(101);
        String actual = getGrade(score);
        String expected = referenceGetGrade(score);

        System.out.println("getGrade(" + score + ") = " + actual + ", ожидалось: " + expected);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }

    @RepeatedTest(5)
    void testGetGradeRepeated() {
        System.out.println("========================Test method start");

        int score = RANDOM.nextInt(101);
        String actual = getGrade(score);
        String expected = referenceGetGrade(score);

        System.out.println("getGrade(" + score + ") = " + actual + ", ожидалось: " + expected);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }

    @ParameterizedTest(name = "getGrade({0})")
    @CsvSource({
            "100, A",
            "85, B",
            "75, C",
            "65, D",
            "50, F"
    })
    void testGetGradeParameterized(int score, String expected) {
        System.out.println("========================Test method start");

        String actual = getGrade(score);
        System.out.println("getGrade(" + score + ") = " + actual + ", ожидалось: " + expected);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }


    @Test
    void testMax() {
        System.out.println("========================Test method start");

        int a = RANDOM.nextInt(100);
        int b = RANDOM.nextInt(100);
        int actual = max(a, b);
        int expected = Math.max(a, b);

        System.out.println("max(" + a + ", " + b + ") = " + actual + ", ожидалось: " + expected);

        if (actual == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }

    @RepeatedTest(5)
    void testMaxRepeated() {
        System.out.println("========================Test method start");

        int a = RANDOM.nextInt(100);
        int b = RANDOM.nextInt(100);
        int actual = max(a, b);
        int expected = Math.max(a, b);

        System.out.println("max(" + a + ", " + b + ") = " + actual + ", ожидалось: " + expected);

        if (actual == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }

    @ParameterizedTest(name = "max({0}, {1})")
    @CsvSource({
            "3, 7, 7",
            "10, 2, 10",
            "5, 5, 5",
            "1, 99, 99"
    })
    void testMaxParameterized(int a, int b, int expected) {
        System.out.println("========================Test method start");

        int actual = max(a, b);
        System.out.println("max(" + a + ", " + b + ") = " + actual + ", ожидалось: " + expected);

        if (actual == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("Test method end");
        System.out.println("========================");
    }


    boolean isEven(int n) {
        return n % 2 == 0;
    }

    String checkAccess(int age) {
        if (age >= 18) {
            return "Access granted";
        }
        return "Access denied";
    }

    String getGrade(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    int max(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }

    private String referenceGetGrade(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }
}
