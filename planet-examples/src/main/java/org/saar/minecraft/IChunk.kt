package org.saar.minecraft

interface IChunk {
    fun getHeight(x: Int, z: Int): Int

    fun getLight(x: Int, y: Int, z: Int): Int

    fun getBlock(x: Int, y: Int, z: Int): Block

    fun setBlock(x: Int, y: Int, z: Int, block: Block)

    fun updateMesh()
}
