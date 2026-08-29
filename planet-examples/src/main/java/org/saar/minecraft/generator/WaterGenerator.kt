package org.saar.minecraft.generator

import org.saar.minecraft.Blocks
import org.saar.minecraft.Chunk

class WaterGenerator(private val waterLevel: Int) : WorldGenerator {
    override fun generateChunk(chunk: Chunk) {
        for (x in 0..15) {
            for (z in 0..15) {
                var y = this.waterLevel
                while (chunk.getBlock(x, y, z) === Blocks.AIR) {
                    chunk.setBlock(x, y--, z, Blocks.WATER)
                }
            }
        }
    }
}
