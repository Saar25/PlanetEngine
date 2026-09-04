package org.saar.minecraft.generator

import org.saar.maths.noise.Noise3f
import org.saar.maths.utils.Maths.clamp
import org.saar.minecraft.Blocks
import org.saar.minecraft.Chunk
import org.saar.minecraft.chunk.ChunkConstants

class Terrain3DGenerator(private val minHeight: Int, private val maxHeight: Int, private val noise: Noise3f) :
    WorldGenerator {
    private fun smoothStep(value: Float): Float {
        val t = clamp((this.maxHeight - value) / (this.maxHeight - this.minHeight), 0f, 1f)
        return t * t * (3 - 2 * t)
    }

    private fun noise(x: Int, y: Int, z: Int): Float {
        return (this.noise.noise(x.toFloat(), y.toFloat(), z.toFloat()) * .5f + .5f) * smoothStep(y.toFloat())
    }

    override fun generateChunk(chunk: Chunk) {
        for (x in 0..<ChunkConstants.SIZE) {
            for (z in 0..<ChunkConstants.SIZE) {
                val wx = x + chunk.position.x() * ChunkConstants.SIZE
                val wz = z + chunk.position.y() * ChunkConstants.SIZE
                for (y in 0..<this.minHeight) {
                    val noise = noise(wx, y, wz)
                    if (noise > .2f) {
                        chunk.setBlockIfEmpty(x, y, z, Blocks.STONE)
                    }
                }
                for (y in this.minHeight..this.maxHeight) {
                    val noise = noise(wx, y, wz)
                    if (noise > .2f) {
                        if (y <= 100 && noise(wx, y + 10, wz) <= .2f) {
                            chunk.setBlockIfEmpty(x, y, z, Blocks.SAND)
                        } else if (noise(wx, y + 1, wz) <= .2f) {
                            chunk.setBlockIfEmpty(x, y, z, Blocks.GRASS)
                        } else if (noise(wx, y + 7, wz) <= .2f) {
                            chunk.setBlockIfEmpty(x, y, z, Blocks.DIRT)
                        } else {
                            chunk.setBlockIfEmpty(x, y, z, Blocks.STONE)
                        }
                    }
                }
            }
        }
    }
}
