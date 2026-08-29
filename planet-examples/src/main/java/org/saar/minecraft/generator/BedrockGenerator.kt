package org.saar.minecraft.generator

import org.saar.maths.noise.Noise2f
import org.saar.minecraft.Blocks
import org.saar.minecraft.Chunk

class BedrockGenerator(private val noise2f: Noise2f = Noise2f.simplex) : WorldGenerator {

    override fun generateChunk(chunk: Chunk) {
        for (x in 0..15) {
            for (z in 0..15) {
                val wx = x + chunk.position.x() * 16
                val wz = z + chunk.position.y() * 16
                val y = (noise(wx, wz) * 3).toInt()
                for (i in 0..y) {
                    chunk.setBlock(x, i, z, Blocks.BEDROCK)
                }
            }
        }
    }

    private fun noise(x: Int, z: Int): Float {
        return this.noise2f.noise(x.toFloat(), z.toFloat()) * .5f + .5f
    }
}
