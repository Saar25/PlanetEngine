package org.saar.minecraft.generator;

import org.joml.SimplexNoise;
import org.saar.minecraft.Blocks;
import org.saar.minecraft.Chunk;

public class BedrockGenerator implements WorldGenerator {

    private static float noise(int x, int z) {
        return SimplexNoise.noise(x, z) * .5f + .5f;
    }

    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                final int wx = x + chunk.getPosition().x() * 16;
                final int wz = z + chunk.getPosition().y() * 16;
                final int y = (int) (noise(wx, wz) * 3);
                for (int i = 0; i <= y; i++) {
                    chunk.setBlock(x, i, z, Blocks.BEDROCK);
                }
            }
        }
    }
}
