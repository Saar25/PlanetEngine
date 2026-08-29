package org.saar.minecraft.generator

import org.saar.minecraft.Chunk

interface WorldGenerator {
    fun generateChunk(chunk: Chunk)
}
