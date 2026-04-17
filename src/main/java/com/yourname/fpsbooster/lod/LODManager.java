package com.yourname.fpsbooster.lod;

public class LODManager {
    
    // Dynamic Level of Detail - closer objects = full quality
    // Far objects = slightly reduced but visually identical
    public static float calculateLOD(float distance, int qualityPreset) {
        if (distance < 32) return 1.0f;     // Full quality
        if (distance < 64) return 0.95f;    // 95% quality (unnoticeable)
        if (distance < 128) return 0.85f;   // 85% quality (still crisp)
        return 0.75f;                        // 75% quality (minimal difference)
    }
}
