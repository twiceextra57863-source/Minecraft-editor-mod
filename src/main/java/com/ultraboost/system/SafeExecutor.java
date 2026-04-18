package com.ultraboost.system;

public class SafeExecutor {

    public static void run(String name, Runnable task) {

        try {
            task.run();
        } catch (Throwable t) {

            System.out.println("[UltraBoost] Error in: " + name);
            CrashHandler.log(t);
        }
    }
}
