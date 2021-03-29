package org.saar.minecraft.generator;

import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

public class WaterGenerator implements WorldGenerator {

    private final int waterLevel;

    public WaterGenerator(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                final int height = chunk.getHeight(x, z);
                for (int y = height; y < this.waterLevel; y++) {
                    if (chunk.getBlock(x, y, z) == Blocks.AIR) {
                        chunk.setBlock(x, y, z, Blocks.WATER);
                    }
                }
            }
        }
    }
}
