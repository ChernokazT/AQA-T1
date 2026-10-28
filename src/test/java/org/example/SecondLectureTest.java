package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты второй лекции: проверка методов через ассерты")
public class SecondLectureTest {

    private static final Random RANDOM = new Random();


    @Test
    @Tag("lecture2")
    @DisplayName("isEven: случайное число — проверка чётности")
    void testIsEven() {
        int n = RANDOM.nextInt(100) + 1;
        boolean actual = isEven(n);
        boolean expected = (n % 2 == 0);

        assertEquals(expected, actual, "isEven(" + n + ") вернул неверный результат: ожидалось " + expected);
    }

    @RepeatedTest(10)
    @Tag("lecture2")
    @DisplayName("isEven: случайное число — 10 повторов")
    void testIsEvenRepeated() {
        int n = RANDOM.nextInt(100) + 1;
        boolean actual = isEven(n);
        boolean expected = (n % 2 == 0);

        assertEquals(expected, actual, "isEven(" + n + ") вернул неверный результат: ожидалось " + expected);
    }

    @ParameterizedTest(name = "isEven({0}) = {1}")
    @Tag("lecture2")
    @DisplayName("isEven: проверка на наборе значений")
    @CsvSource({
            "2, true",
            "3, false",
            "100, true",
            "57, false",
            "0, true",
            "1, false",
            "4, true",
            "999, false",
            "256, true",
            "255, false"
    })
    void testIsEvenParameterized(int n, boolean expected) {
        boolean actual = isEven(n);
        assertEquals(expected, actual, "isEven(" + n + ") вернул неверный результат: ожидалось " + expected);
    }


    @Test
    @Tag("lecture2")
    @DisplayName("checkAccess: случайный возраст — проверка доступа")
    void testCheckAccess() {
        int age = RANDOM.nextInt(100);
        String actual = checkAccess(age);
        String expected = (age >= 18) ? "Access granted" : "Access denied";

        assertEquals(expected, actual, "checkAccess(" + age + ") вернул неверный результат: ожидалось '" + expected + "'");
    }

    @RepeatedTest(10)
    @Tag("lecture2")
    @DisplayName("checkAccess: случайный возраст — 10 повторов")
    void testCheckAccessRepeated() {
        int age = RANDOM.nextInt(100);
        String actual = checkAccess(age);
        String expected = (age >= 18) ? "Access granted" : "Access denied";

        assertEquals(expected, actual, "checkAccess(" + age + ") вернул неверный результат: ожидалось '" + expected + "'");
    }

    @ParameterizedTest(name = "checkAccess({0})")
    @Tag("lecture2")
    @DisplayName("checkAccess: проверка на границе 18 лет")
    @CsvSource({
            "0, Access denied",
            "17, Access denied",
            "18, Access granted",
            "99, Access granted",
            "1, Access denied",
            "10, Access denied",
            "19, Access granted",
            "50, Access granted",
            "80, Access granted",
            "100, Access granted"
    })
    void testCheckAccessParameterized(int age, String expected) {
        String actual = checkAccess(age);
        assertEquals(expected, actual, "checkAccess(" + age + ") вернул неверный результат: ожидалось '" + expected + "'");
    }


    @Test
    @Tag("lecture2")
    @DisplayName("getGrade: случайный балл — проверка оценки")
    void testGetGrade() {
        int score = RANDOM.nextInt(101);
        String actual = getGrade(score);
        String expected = referenceGetGrade(score);

        assertEquals(expected, actual, "getGrade(" + score + ") вернул неверную оценку: ожидалось '" + expected + "'");
    }

    @RepeatedTest(10)
    @Tag("lecture2")
    @DisplayName("getGrade: случайный балл — 10 повторов")
    void testGetGradeRepeated() {
        int score = RANDOM.nextInt(101);
        String actual = getGrade(score);
        String expected = referenceGetGrade(score);

        assertEquals(expected, actual, "getGrade(" + score + ") вернул неверную оценку: ожидалось '" + expected + "'");
    }

    @ParameterizedTest(name = "getGrade({0}) = {1}")
    @Tag("lecture2")
    @DisplayName("getGrade: проверка всех диапазонов оценок")
    @CsvSource({
            "100, A",
            "85, B",
            "75, C",
            "65, D",
            "50, F",
            "90, A",
            "89, B",
            "80, B",
            "79, C",
            "0, F"
    })
    void testGetGradeParameterized(int score, String expected) {
        String actual = getGrade(score);
        assertEquals(expected, actual, "getGrade(" + score + ") вернул неверную оценку: ожидалось '" + expected + "'");
    }


    @Test
    @Tag("lecture3")
    @DisplayName("max: сравнение двух случайных чисел")
    void testMax() {
        int a = RANDOM.nextInt(100);
        int b = RANDOM.nextInt(100);
        int actual = max(a, b);
        int expected = Math.max(a, b);

        assertEquals(expected, actual, "max(" + a + ", " + b + ") вернул неверный результат: ожидалось " + expected);
    }

    @RepeatedTest(10)
    @Tag("lecture2")
    @DisplayName("max: сравнение случайных чисел — 10 повторов")
    void testMaxRepeated() {
        int a = RANDOM.nextInt(100);
        int b = RANDOM.nextInt(100);
        int actual = max(a, b);
        int expected = Math.max(a, b);

        assertEquals(expected, actual, "max(" + a + ", " + b + ") вернул неверный результат: ожидалось " + expected);
    }

    @ParameterizedTest(name = "max({0}, {1}) = {2}")
    @Tag("lecture2")
    @DisplayName("max: проверка на наборе значений, включая границы")
    @CsvSource({
            "3, 7, 7",
            "10, 2, 10",
            "5, 5, 5",
            "1, 99, 99",
            "0, 0, 0",
            "99, 0, 99",
            "-5, 5, 5",
            "-10, -3, -3",
            "100, 100, 100",
            "7, 3, 7"
    })
    void testMaxParameterized(int a, int b, int expected) {
        int actual = max(a, b);
        assertEquals(expected, actual, "max(" + a + ", " + b + ") вернул неверный результат: ожидалось " + expected);
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
