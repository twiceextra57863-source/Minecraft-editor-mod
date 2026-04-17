package com.ultraboost.render;

import java.util.ArrayList;
import java.util.List;

public class BatchManager {

    private static final List<Runnable> calls = new ArrayList<>();

    public static void add(Runnable r) {
        calls.add(r);
    }

    public static void flush() {
        for (Runnable r : calls) {
            r.run();
        }
        calls.clear();
    }
}
