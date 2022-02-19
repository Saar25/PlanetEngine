package org.saar.minecraft.generator;

import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

public class FlatTerrainGenerator implements WorldGenerator {

    private final int height;

    public FlatTerrainGenerator(int height) {
        this.height = height;
    }

    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < this.height; y++) {
                    chunk.setBlock(x, y, z, Blocks.DIRT);
                }
                chunk.setBlock(x, this.height, z, Blocks.GRASS);
            }
        }
    }
}
