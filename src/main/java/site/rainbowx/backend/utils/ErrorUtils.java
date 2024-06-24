package site.rainbowx.backend.utils;

public class ErrorUtils {
    public static void Ensure(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
        return;
    }
}
