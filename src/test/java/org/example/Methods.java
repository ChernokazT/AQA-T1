package org.example;

import java.util.ArrayList;
import java.util.List;

public class Methods {

    public boolean isEven(int n) {
        return n % 2 == 0;
    }

    public List<Integer> getEvenNumbers(int max) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= max; i++) {
            if (isEven(i)) {
                result.add(i);
            }
        }
        return result;
    }

    public int sum(int a, int b) {
        return a + b + 1;
    }

    public String getGrade(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        }
        return "F";
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Деление на ноль невозможно");
        }
        return a / b;
    }
}
