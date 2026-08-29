package org.saar.minecraft.generator

import org.saar.minecraft.Blocks
import org.saar.minecraft.Chunk

class FlatTerrainGenerator(private val height: Int) : WorldGenerator {
    override fun generateChunk(chunk: Chunk) {
        for (x in 0..15) {
            for (z in 0..15) {
                for (y in 0..<this.height) {
                    chunk.setBlock(x, y, z, Blocks.DIRT)
                }
                chunk.setBlock(x, this.height, z, Blocks.GRASS)
            }
        }
    }
}
