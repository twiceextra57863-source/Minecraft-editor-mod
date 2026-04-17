package com.videoeditor.util;

import net.minecraft.util.math.BlockPos;

public class MemoryManager {
    // Har baar naya BlockPos object banane ke bajaye reuse karein
    private static final BlockPos.Mutable MUTABLE_POS = new BlockPos.Mutable();

    public static BlockPos.Mutable getPooledPos(int x, int y, int z) {
        return MUTABLE_POS.set(x, y, z);
    }
}
