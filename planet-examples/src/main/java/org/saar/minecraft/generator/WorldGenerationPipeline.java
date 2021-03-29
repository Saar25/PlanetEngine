package org.saar.minecraft.generator;

import org.saar.minecraft.Chunk;

public interface WorldGenerationPipeline extends WorldGenerator {

    static WorldGenerationPipeline pipe(WorldGenerator generator) {
        return generator::generateChunk;
    }

    default WorldGenerationPipeline then(WorldGenerator generator) {
        return chunk -> {
            generateChunk(chunk);
            generator.generateChunk(chunk);
        };
    }

    @Override
    void generateChunk(Chunk chunk);

}
