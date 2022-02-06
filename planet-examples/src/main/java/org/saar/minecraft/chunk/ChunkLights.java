package org.saar.minecraft.chunk;

import org.joml.Vector3ic;
import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

import java.util.Arrays;

public class ChunkLights {

    private final Chunk chunk;

    private final byte[] lights = new byte[16 * 16 * 256];

    public ChunkLights(Chunk chunk) {
        this.chunk = chunk;
        Arrays.fill(this.lights, (byte) 0xF);
    }

    private static int index(int x, int y, int z) {
        return ((x & 0xF) << 12) | ((z & 0xF) << 8) | (y & 0xFF);
    }

    private int calculateLight(int x, int y, int z) {
        if (this.chunk.getBlock(x, y, z) != Blocks.AIR) return 0;
        int max = this.chunk.getLight(x, y + 1, z);
        for (Vector3ic direction : ChunkConstants.blockDirections) {
            final int light = this.chunk.getLight(
                    x + direction.x(),
                    y + direction.y(),
                    z + direction.z()
            );
            max = Math.max(max, light - 1);
        }
        return max;
    }

    public void updateLight(int x, int y, int z) {
        updateLight(x, y, z, calculateLight(x, y, z));
    }

    public void updateLight(int x, int y, int z, int light) {
        final int index = index(x, y, z);
        if (this.lights[index] == light) return;

        final int calcLight = calculateLight(x, y, z);
        if (this.lights[index] == calcLight) return;

        this.lights[index] = (byte) calcLight;

        this.chunk.updateLight(x, y - 1, z, calcLight);
        this.chunk.updateLight(x, y + 1, z, Math.max(calcLight - 1, 0));
        this.chunk.updateLight(x - 1, y, z, Math.max(calcLight - 1, 0));
        this.chunk.updateLight(x + 1, y, z, Math.max(calcLight - 1, 0));
        this.chunk.updateLight(x, y, z - 1, Math.max(calcLight - 1, 0));
        this.chunk.updateLight(x, y, z + 1, Math.max(calcLight - 1, 0));
    }

    public int getLight(int x, int y, int z) {
        final int index = index(x, y, z);
        return this.lights[index];
    }
}
