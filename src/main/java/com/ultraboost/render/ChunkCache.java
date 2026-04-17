package com.ultraboost.render;

import java.util.HashSet;
import java.util.Set;

public class ChunkCache {

    private static final Set<String> renderedChunks = new HashSet<>();

    public static boolean shouldRender(int x, int z) {
        String key = x + "," + z;

        if (renderedChunks.contains(key)) {
            return false; // already rendered
        }

        renderedChunks.add(key);
        return true;
    }

    public static void clear() {
        renderedChunks.clear();
    }
}
