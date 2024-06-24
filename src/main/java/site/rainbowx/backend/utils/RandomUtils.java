package site.rainbowx.backend.utils;

import java.util.Random;

public class RandomUtils {
    public static Random random = new Random();

    public static void RandomSleep(int lower_bound_ms, int upper_bound_ms) {
        if(lower_bound_ms >= upper_bound_ms) throw new RuntimeException("Invalid bound for random.");
        int sleep_ms = random.nextInt(lower_bound_ms, upper_bound_ms);
        try { Thread.sleep(sleep_ms); }
        catch (InterruptedException ignored) {}
        return;
    }
}
