package org.saar.minecraft.generator

import org.saar.maths.noise.Noise3f
import org.saar.minecraft.Blocks
import org.saar.minecraft.Chunk
import org.saar.minecraft.chunk.ChunkConstants
import kotlin.math.min

class TerrainGenerator(private val minHeight: Int, private val maxHeight: Int, private val noise: Noise3f) :
    WorldGenerator {
    private fun noise(x: Int, z: Int): Float {
        return this.noise.noise(x.toFloat(), 0f, z.toFloat()) * .5f + .5f
    }

    private fun height(wx: Int, wz: Int): Int {
        return (noise(wx, wz) * (this.maxHeight - this.minHeight) + this.minHeight).toInt()
    }

    override fun generateChunk(chunk: Chunk) {
        for (x in 0..<ChunkConstants.SIZE) {
            for (z in 0..<ChunkConstants.SIZE) {
                val wx = x + chunk.position.x() * ChunkConstants.SIZE
                val wz = z + chunk.position.y() * ChunkConstants.SIZE
                val height = height(wx, wz)

                for (y in 0..<height - 8) {
                    chunk.setBlockIfEmpty(x, y, z, Blocks.STONE)
                }

                if (height < 102) {
                    val sandLevel = min(102 - height, 4)
                    for (y in height - 8..<height - sandLevel) {
                        chunk.setBlockIfEmpty(x, y, z, Blocks.DIRT)
                    }
                    for (y in height - sandLevel..height) {
                        chunk.setBlockIfEmpty(x, y, z, Blocks.SAND)
                    }
                } else {
                    for (y in height - 8..<height) {
                        chunk.setBlockIfEmpty(x, y, z, Blocks.DIRT)
                    }
                    chunk.setBlockIfEmpty(x, height, z, Blocks.GRASS)
                }
            }
        }
    }
}
