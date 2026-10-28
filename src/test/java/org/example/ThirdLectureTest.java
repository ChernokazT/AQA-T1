package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThirdLectureTest {

    private static final Random RANDOM = new Random();
    Methods methods = new Methods();


    @Test
    @Tag("task1")
    @DisplayName("isEven: чётное число возвращает true")
    void testIsEvenTrue() {
        int n = RANDOM.nextInt(50) * 2;
        assertTrue(methods.isEven(n), "isEven(" + n + ") должен вернуть true, т.к. число чётное");
    }

    @Test
    @Tag("task1")
    @DisplayName("isEven: нечётное число возвращает false")
    void testIsEvenFalse() {
        int n = RANDOM.nextInt(50) * 2 + 1;
        assertFalse(methods.isEven(n), "isEven(" + n + ") должен вернуть false, т.к. число нечётное");
    }

    @Test
    @Tag("task1")
    @DisplayName("getEvenNumbers: список чётных чисел от 0 до 10")
    void testGetEvenNumbers() {
        List<Integer> actual = methods.getEvenNumbers(10);
        List<Integer> expected = Arrays.asList(0, 2, 4, 6, 8, 10);
        assertEquals(expected, actual, "Список чётных чисел от 0 до 10 посчитан неверно: ожидалось " + expected);
    }

    // Специально падающий тест: метод sum возвращает неверное значение
    @Test
    @Tag("negative")
    @Tag("task1")
    @DisplayName("sum: метод возвращает неверное значение, ассерт падает")
    void testSumFails() {
        int a = RANDOM.nextInt(10);
        int b = RANDOM.nextInt(10);
        assertEquals(a + b, methods.sum(a, b),
                "sum(" + a + ", " + b + ") должна вернуть " + (a + b) + ", но вернула другое значение");
    }

    // ================= ТЕМА EXCEPTION =================

    @Test
    @Tag("lecture3")
    @DisplayName("divide: корректное деление")
    void testDividePositive() {
        assertEquals(5, methods.divide(10, 2), "divide(10, 2) должен вернуть 5");
    }

    @Test
    @Tag("lecture3")
    @DisplayName("divide: деление на ноль выбрасывает исключение")
    void testDivideByZeroThrows() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> methods.divide(10, 0),
                "divide(10, 0) должен выбросить IllegalArgumentException"
        );
        assertEquals("Деление на ноль невозможно", exception.getMessage(),
                "Сообщение исключения должно быть 'Деление на ноль невозможно'");
    }

    @Test
    @Tag("lecture3")
    @DisplayName("divide: обработка исключения через try-catch")
    void testDivideWithTryCatch() {
        try {
            methods.divide(10, 0);
            assertTrue(false, "Ожидалось исключение, но divide(10, 0) выполнился без ошибки");
        } catch (IllegalArgumentException e) {
            assertEquals("Деление на ноль невозможно", e.getMessage(),
                    "Ожидалось сообщение 'Деление на ноль невозможно'");
        }
    }
}
