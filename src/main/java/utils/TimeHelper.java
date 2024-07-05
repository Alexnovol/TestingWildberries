package utils;

import java.util.concurrent.TimeUnit;

public class TimeHelper {

    public static void setDelay(int sec) {

        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
