package org.saar.minecraft.generator;

import org.joml.SimplexNoise;
import org.saar.maths.utils.Maths;
import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

public class TerrainGenerator implements WorldGenerator {

    private final int waterLevel;

    public TerrainGenerator(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    private static float smoothStep(float value, float edge0, float edge1) {
        final float t = Maths.clamp((value - edge0) / (edge1 - edge0), 0, 1);
        return t * t * (3 - 2 * t);
    }

    private static float noise(int x, int y, int z) {
        final float noise = (SimplexNoise.noise(x / 32f, y / 32f, z / 32f)
                + SimplexNoise.noise(x / 64f, y / 64f, z / 64f)
                + SimplexNoise.noise(x / 128f, y / 128f, z / 128f)) / 3 * .5f + .5f;
        return noise * smoothStep(y, 100, 60);
    }

    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                final int wx = x + chunk.getPosition().x() * 16;
                final int wz = z + chunk.getPosition().y() * 16;
                for (int y = 0; y < 53; y++) {
                    final float noise = noise(wx, y, wz);
                    if (noise > .2f) {
                        chunk.setBlock(x, y, z, Blocks.STONE);
                    }
                }
                for (int y = 53; y < 120; y++) {
                    final float noise = noise(wx, y, wz);
                    if (noise > .2f) {
                        if (y >= this.waterLevel - 1 && y > 60 && noise(wx, y + 1, wz) <= .2f) {
                            chunk.setBlock(x, y, z, Blocks.GRASS);
                        } else if (noise(wx, y + 7, wz) <= .2f) {
                            chunk.setBlock(x, y, z, Blocks.DIRT);
                        } else {
                            chunk.setBlock(x, y, z, Blocks.STONE);
                        }
                    }
                }
            }
        }
    }
}
