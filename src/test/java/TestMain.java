import org.junit.jupiter.api.Test;

public class TestMain {
    @Test
    public static boolean isEven(int n) {
        if (n % 2 == 0){
            return true;
        }else {
            return false;
        }

    }
    @Test
    public static String checkAccess(int age) {
        return (age > 18) ? "Allowed" : "Denied";
    }
}