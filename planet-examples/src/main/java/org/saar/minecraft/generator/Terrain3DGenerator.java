package org.saar.minecraft.generator;

import org.saar.maths.noise.Noise3f;
import org.saar.maths.utils.Maths;
import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

public class Terrain3DGenerator implements WorldGenerator {

    private final int minHeight;
    private final int maxHeight;
    private final Noise3f noise;

    public Terrain3DGenerator(int minHeight, int maxHeight, Noise3f noise) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.noise = noise;
    }

    private float smoothStep(float value) {
        final float t = Maths.clamp((this.maxHeight - value) / (this.maxHeight - this.minHeight), 0, 1);
        return t * t * (3 - 2 * t);
    }

    private float noise(int x, int y, int z) {
        return (this.noise.noise(x, y, z) * .5f + .5f) * smoothStep(y);
    }

    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                final int wx = x + chunk.getPosition().x() * 16;
                final int wz = z + chunk.getPosition().y() * 16;
                for (int y = 0; y < this.minHeight; y++) {
                    final float noise = noise(wx, y, wz);
                    if (noise > .2f) {
                        chunk.setBlock(x, y, z, Blocks.STONE);
                    }
                }
                for (int y = this.minHeight; y <= this.maxHeight; y++) {
                    final float noise = noise(wx, y, wz);
                    if (noise > .2f) {
                        if (y <= 100 && noise(wx, y + 10, wz) <= .2f) {
                            chunk.setBlock(x, y, z, Blocks.SAND);
                        } else if (noise(wx, y + 1, wz) <= .2f) {
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
