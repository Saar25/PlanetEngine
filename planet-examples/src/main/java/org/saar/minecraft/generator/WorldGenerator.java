package org.saar.minecraft.generator;

import org.saar.minecraft.Chunk;

public interface WorldGenerator {

    void generateChunk(Chunk chunk);
}
