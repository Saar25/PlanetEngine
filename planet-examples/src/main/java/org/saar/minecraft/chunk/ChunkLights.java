package org.saar.minecraft.chunk;

import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

import java.util.Arrays;

public class ChunkLights {

    private final Chunk chunk;

    private final byte[] lights = new byte[16 * 16 * 256];

    public ChunkLights(Chunk chunk) {
        this.chunk = chunk;
        Arrays.fill(this.lights, (byte) 0xFF);
    }

    private static int index(int x, int y, int z) {
        return ((x & 0xF) << 12) | ((z & 0xF) << 8) | (y & 0xFF);
    }

    private void updateAround(int x, int y, int z, int calcLight) {
        this.chunk.updateLight(x, y - 1, z, calcLight);
        this.chunk.updateLight(x, y + 1, z, Math.max(calcLight - 0x10, 0));
        this.chunk.updateLight(x - 1, y, z, Math.max(calcLight - 0x10, 0));
        this.chunk.updateLight(x + 1, y, z, Math.max(calcLight - 0x10, 0));
        this.chunk.updateLight(x, y, z - 1, Math.max(calcLight - 0x10, 0));
        this.chunk.updateLight(x, y, z + 1, Math.max(calcLight - 0x10, 0));
    }

    private int calculateLight(int x, int y, int z) {
        if (this.chunk.getBlock(x, y, z) == Blocks.AIR) {
            int max = this.chunk.getLight(x, y + 1, z);
            max = Math.max(max, this.chunk.getLight(x, y - 1, z) - 0x10);
            max = Math.max(max, this.chunk.getLight(x - 1, y, z) - 0x10);
            max = Math.max(max, this.chunk.getLight(x + 1, y, z) - 0x10);
            max = Math.max(max, this.chunk.getLight(x, y, z - 1) - 0x10);
            max = Math.max(max, this.chunk.getLight(x, y, z + 1) - 0x10);
            return Math.max(max, 0);
        } else if (this.chunk.getBlock(x, y, z) == Blocks.WATER) {
            int max = this.chunk.getLight(x, y + 1, z) - 0xA;
            max = Math.max(max, this.chunk.getLight(x, y - 1, z) - 0xA);
            max = Math.max(max, this.chunk.getLight(x - 1, y, z) - 0xA);
            max = Math.max(max, this.chunk.getLight(x + 1, y, z) - 0xA);
            max = Math.max(max, this.chunk.getLight(x, y, z - 1) - 0xA);
            max = Math.max(max, this.chunk.getLight(x, y, z + 1) - 0xA);
            return Math.max(max, 0);
        }
        return 0;
    }

    public void updateLight(int x, int y, int z) {
        updateLight(x, y, z, calculateLight(x, y, z));
    }

    public void updateLight(int x, int y, int z, int light) {
        final int index = index(x, y, z);
        if (getLight(index) == light) return;

        final int calcLight = calculateLight(x, y, z);
        if (getLight(index) == calcLight) return;

        this.lights[index] = (byte) calcLight;
        updateAround(x, y, z, calcLight);
    }

    public int getLight(int x, int y, int z) {
        final int index = index(x, y, z);
        return getLight(index);
    }

    private int getLight(int index) {
        return this.lights[index] & 0xFF;
    }
}
