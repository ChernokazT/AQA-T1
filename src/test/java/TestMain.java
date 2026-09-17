import org.junit.jupiter.api.Test;

public class TestMain {
    public static void main(String[] args) {
        blastOff1(5);
    }
    public static boolean isEven(int n) {
        if (n % 2 == 0){
            return true;
        }else {
            return false;
        }

    }
    @Test

    public static String checkAccess(int age) {
        if (age > 18){
            return "Allowed";
        }else {
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
        return (num + " Поехали!");

    }
    public static String blastOff1(int start) {
        String conStr = " Поехали!";
        for (int i = 0; i < start; i++) {
            result += (start - i);
        }

        return "";
    }

    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }



}
