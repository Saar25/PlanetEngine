package org.saar.minecraft.chunk

import org.saar.minecraft.Blocks
import org.saar.minecraft.Chunk

class ChunkHeights(private val chunk: Chunk) {

    private val heights = ByteArray(16 * 16)

    private fun findHeight(x: Int, z: Int): Byte {
        for (i in 255 downTo 0) {
            if (this.chunk.getBlock(x, i, z) !== Blocks.AIR) {
                return i.toByte()
            }
        }
        return 0
    }

    fun addBlock(x: Int, y: Int, z: Int) {
        val heightIndex: Int = index(x, z)
        if (this.heights[heightIndex] < y) {
            this.heights[heightIndex] = y.toByte()
        }
    }

    fun removeBlock(x: Int, y: Int, z: Int) {
        val heightIndex: Int = index(x, z)
        if (this.heights[heightIndex].toInt() == y) {
            this.heights[heightIndex] = findHeight(x, z)
        }
    }

    fun getHeight(x: Int, z: Int): Int {
        val index: Int = index(x, z)
        return this.heights[index].toInt() and 0xFF
    }
}

private fun index(x: Int, z: Int): Int {
    return ((x and 0xF) shl 4) or (z and 0xF)
}
