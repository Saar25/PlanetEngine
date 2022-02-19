package org.saar.minecraft.generator;

import org.saar.maths.noise.Noise3f;
import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

public class TerrainGenerator implements WorldGenerator {

    private final int minHeight;
    private final int maxHeight;
    private final Noise3f noise;

    public TerrainGenerator(int minHeight, int maxHeight, Noise3f noise) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.noise = noise;
    }

    private float noise(int x, int z) {
        return this.noise.noise(x, 0, z) * .5f + .5f;
    }

    private int height(int wx, int wz) {
        return (int) (noise(wx, wz) * (this.maxHeight - this.minHeight) + this.minHeight);
    }

    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                final int wx = x + chunk.getPosition().x() * 16;
                final int wz = z + chunk.getPosition().y() * 16;
                final int height = height(wx, wz);

                for (int y = 0; y < height - 8; y++) {
                    chunk.setBlockIfEmpty(x, y, z, Blocks.STONE);
                }

                if (height < 102) {
                    final int sandLevel = Math.min(102 - height, 4);
                    for (int y = height - 8; y < height - sandLevel; y++) {
                        chunk.setBlockIfEmpty(x, y, z, Blocks.DIRT);
                    }
                    for (int y = height - sandLevel; y <= height; y++) {
                        chunk.setBlockIfEmpty(x, y, z, Blocks.SAND);
                    }
                } else {
                    for (int y = height - 8; y < height; y++) {
                        chunk.setBlockIfEmpty(x, y, z, Blocks.DIRT);
                    }
                    chunk.setBlockIfEmpty(x, height, z, Blocks.GRASS);
                }
            }
        }
    }
}
