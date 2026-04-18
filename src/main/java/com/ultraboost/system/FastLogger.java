package com.ultraboost.system;

public class FastLogger {

    public static void log(String msg) {
        System.out.println("[UltraBoost] " + msg);
    }

    public static void error(String msg, Throwable t) {
        System.out.println("[UltraBoost ERROR] " + msg);
        t.printStackTrace();
    }
}
