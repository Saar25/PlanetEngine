package org.saar.minecraft

object EmptyChunk : IChunk {
    override fun getHeight(x: Int, z: Int) = 0

    override fun getLight(x: Int, y: Int, z: Int) = 0

    override fun getBlock(x: Int, y: Int, z: Int) = Blocks.AIR

    override fun setBlock(x: Int, y: Int, z: Int, block: Block) = Unit

    override fun updateMesh() = Unit
}
