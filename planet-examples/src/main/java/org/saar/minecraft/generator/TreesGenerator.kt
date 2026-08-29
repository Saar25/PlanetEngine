package org.saar.minecraft.generator

import org.joml.SimplexNoise
import org.saar.maths.noise.Noise2f
import org.saar.minecraft.Blocks
import org.saar.minecraft.Chunk

class TreesGenerator(private val noise2f: Noise2f) : WorldGenerator {
    override fun generateChunk(chunk: Chunk) {
        for (x in 0..15) {
            for (z in 0..15) {
                val wx = x + chunk.position.x() * 16
                val wz = z + chunk.position.y() * 16
                if (noise(wx, wz) > .70f) {
                    val y = chunk.getHeight(x, z) + 1
                    val block = chunk.getBlock(x, y - 1, z)
                    if (y > 60 && (block === Blocks.GRASS || block === Blocks.DIRT)) {
                        for (i in 0..3) {
                            chunk.setBlockIfEmpty(x, y + i, z, Blocks.TREE)
                        }
                        for (ty in 0..2) {
                            for (tx in -ty..ty) {
                                for (tz in -ty..ty) {
                                    chunk.setBlockIfEmpty(x + tx, y + (3 - ty) + 3, z + tz, Blocks.LEAVES)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private fun noise(x: Int, z: Int): Float {
            return ((SimplexNoise.noise(x / 2f, z / 2f)
                    + SimplexNoise.noise(x / 4f, z / 4f)
                    + SimplexNoise.noise(x / 8f, z / 8f))) / 3 * .5f + .5f
        }
    }
}
