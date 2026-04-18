package com.ultraboost.system;

public class CrashHandler {

    public static void init() {

        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {

            System.out.println("[UltraBoost] Crash detected in thread: " + thread.getName());

            log(throwable);

            // prevent full crash (partial)
            System.out.println("[UltraBoost] Attempting recovery...");
        });
    }

    public static void log(Throwable t) {

        System.out.println("==== UltraBoost Crash Log ====");

        System.out.println("Error: " + t.getMessage());

        for (StackTraceElement e : t.getStackTrace()) {
            System.out.println(e.toString());
        }

        System.out.println("==== END ====");
    }
}
