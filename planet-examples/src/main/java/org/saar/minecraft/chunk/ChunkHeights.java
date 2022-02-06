package org.saar.minecraft.chunk;

import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

public class ChunkHeights {

    private final Chunk chunk;

    private final byte[] heights = new byte[16 * 16];

    public ChunkHeights(Chunk chunk) {
        this.chunk = chunk;
    }

    private static int index(int x, int z) {
        return ((x & 0xF) << 4) | (z & 0xF);
    }

    private byte findHeight(int x, int z) {
        for (int i = 255; i >= 0; i--) {
            if (this.chunk.getBlock(x, i, z) != Blocks.AIR) {
                return (byte) i;
            }
        }
        return 0;
    }

    public void addBlock(int x, int y, int z) {
        final int heightIndex = index(x, z);
        if (this.heights[heightIndex] < y) {
            this.heights[heightIndex] = (byte) y;
        }
    }

    public void removeBlock(int x, int y, int z) {
        final int heightIndex = index(x, z);
        if (this.heights[heightIndex] == y) {
            this.heights[heightIndex] = findHeight(x, z);
        }
    }

    public int getHeight(int x, int z) {
        final int index = index(x, z);
        return this.heights[index] & 0xFF;
    }
}
