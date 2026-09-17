import org.junit.jupiter.api.Test;

public class TestMain {
    public static void main(String[] args) {
        sumToN(5);
        String[] test = {"ok", "BUG", "fine"};
        System.out.println("Тест (имеется BUG заглавными): " + hasBug(test));
        String[] test2 = {"ok", "NotBUG", "fine"};
        System.out.println("Тест (Бага нет): " + hasBug(test));
        System.out.println(getEvenInRange1(1, 5));
        int[] data = {1, 2, 5, 7, 10};
        System.out.println(findMax(data));
    }

    public static boolean isEven(int n) {
        if (n % 2 == 0) {
            return true;
        } else {
            return false;
        }

    }

    @Test

    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }

    public static boolean isPositive(int n) {
        return (n >= 0) ? true : false;
    }

    public static String getGrade(int score) {
        if (score >= 0 && score <= 20) {
            return "E";
        } else if (score >= 21 && score <= 40) {
            return "D";
        } else if (score >= 41 && score <= 60) {
            return "C";
        } else if (score >= 61 && score <= 80) {
            return "B";
        } else if (score >= 81 && score <= 100) {
            return "A";
        } else {
            return "Error";
        }
    }

    public static String blastOff(int start) {
        int[] arr = new int[start];
        String num = null;
        for (int i = 1; i > 0; i--) {
            num = String.valueOf(arr[i]);
        }
        return num;

    }
//    public static String blastOff1(int start) {
//        String conStr = " Поехали!";
//        for (int i = 0; i < start; i++) {
//            result += (start - i);
//        }
//
//        return "";
//    }

    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println(sum);
        return sum;
    }

    @Test
    public static boolean hasBug(String[] messages) {
        for (String n : messages) {
            if (n.equalsIgnoreCase("Bug!")) {
                return false;
            }
        }
        return true;
    }

    public static String getEvenInRange(int start, int end) {
        if (start % 2 == 0) {
            return String.valueOf(start);
        } else if (start % 2 != 0) {
            return String.valueOf(start - 1);
        }
        if (end % 2 == 0) {
            return String.valueOf(end);
        } else if (end % 2 != 0) {
            return String.valueOf(end + 1);
        }
        return null;

    }

    public static String getEvenInRange1(int start, int end) {
        String result = "";
        int firstEven = (start % 2 == 0) ? start : start + 1;
        for (int i = firstEven; i <= end; i += 2) {
            if (!result.isEmpty()) {
                result += " ";
            }
            result += i;
        }

        return result;
    }
    public static int findMax(int[] arr) {
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++ ){
            if (maxValue < arr[i]) {
                maxValue = arr[i];
            }
        }
        return maxValue;
    }
    public static String[] reverse(String[] arr) {
        String[] result = new String[arr.length];

    }


}
