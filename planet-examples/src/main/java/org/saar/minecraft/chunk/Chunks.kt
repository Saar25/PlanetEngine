package org.saar.minecraft.chunk

import org.saar.core.mesh.buffer.MeshVertexBuffer

object Chunks {

    @JvmStatic
    fun vertex(x: Int, y: Int, z: Int, blockId: Int, direction: Int, shadow: Int) =
        object : ChunkVertex {
            override val x = x
            override val y = y
            override val z = z
            override val blockId = blockId
            override val direction = direction
            override val shadow = shadow
        }

    @JvmStatic
    fun meshPrototype() = ChunkMeshPrototype(MeshVertexBuffer.createStatic())
}