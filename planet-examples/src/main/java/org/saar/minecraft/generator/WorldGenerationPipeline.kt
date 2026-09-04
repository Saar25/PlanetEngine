package org.saar.minecraft.generator

import org.saar.minecraft.Chunk

fun interface WorldGenerationPipeline : WorldGenerator {
    fun then(generator: WorldGenerator) = WorldGenerationPipeline { chunk ->
        generateChunk(chunk)
        generator.generateChunk(chunk)
    }

    override fun generateChunk(chunk: Chunk)

    companion object {
        @JvmStatic
        fun pipe(generator: WorldGenerator): WorldGenerationPipeline {
            return WorldGenerationPipeline { chunk -> generator.generateChunk(chunk) }
        }
    }
}
