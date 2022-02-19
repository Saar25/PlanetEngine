package org.saar.minecraft.generator;

import org.joml.SimplexNoise;
import org.saar.maths.noise.Noise2f;
import org.saar.minecraft.Block;
import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

public class TreesGenerator implements WorldGenerator {

    private final Noise2f noise2f;

    public TreesGenerator(Noise2f noise2f) {
        this.noise2f = noise2f;
    }

    private static float noise(int x, int z) {
        return (SimplexNoise.noise(x / 2f, z / 2f)
                + SimplexNoise.noise(x / 4f, z / 4f)
                + SimplexNoise.noise(x / 8f, z / 8f)) / 3 * .5f + .5f;
    }

    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                final int wx = x + chunk.getPosition().x() * 16;
                final int wz = z + chunk.getPosition().y() * 16;
                if (noise(wx, wz) > .70f) {
                    final int y = chunk.getHeight(x, z) + 1;
                    final Block block = chunk.getBlock(x, y - 1, z);
                    if (y > 60 && (block == Blocks.GRASS || block == Blocks.DIRT)) {
                        for (int i = 0; i < 4; i++) {
                            chunk.setBlockIfEmpty(x, y + i, z, Blocks.TREE);
                        }
                        for (int ty = 0; ty < 3; ty++) {
                            for (int tx = -ty; tx <= ty; tx++) {
                                for (int tz = -ty; tz <= ty; tz++) {
                                    chunk.setBlockIfEmpty(x + tx, y + (3 - ty) + 3, z + tz, Blocks.LEAVES);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
