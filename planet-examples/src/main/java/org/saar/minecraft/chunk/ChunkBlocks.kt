package org.saar.minecraft.chunk

import org.saar.minecraft.Block
import org.saar.minecraft.BlockContainer
import org.saar.minecraft.Blocks

class ChunkBlocks private constructor(private val blocks: Array<Block>) {

    constructor() : this(Array(ChunkConstants.SIZE * ChunkConstants.SIZE * ChunkConstants.HEIGHT) { Blocks.AIR })

    val opaque = mutableListOf<BlockContainer>()

    val water = mutableListOf<BlockContainer>()

    fun setBlock(x: Int, y: Int, z: Int, block: Block) {
        val index: Int = index(x, y, z)
        if (this.blocks[index] !== Blocks.AIR) {
            this.opaque.removeIf { bc: BlockContainer ->
                bc.position.equals(x, y, z)
            }
            this.water.removeIf { bc: BlockContainer ->
                bc.position.equals(x, y, z)
            }
        }

        if (block === Blocks.WATER) {
            this.water.add(BlockContainer(x, y, z, block))
        } else if (block !== Blocks.AIR) {
            this.opaque.add(BlockContainer(x, y, z, block))
        }
        this.blocks[index] = block
    }

    fun getBlock(x: Int, y: Int, z: Int): Block {
        val index: Int = index(x, y, z)
        return this.blocks[index]
    }

    companion object {
        private fun index(x: Int, y: Int, z: Int): Int {
            return ((x and 0xF) shl 12) or ((z and 0xF) shl 8) or (y and 0xFF)
        }
    }
}
