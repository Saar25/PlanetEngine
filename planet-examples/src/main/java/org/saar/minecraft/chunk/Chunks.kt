package org.saar.minecraft.chunk

import org.saar.core.mesh.buffer.MeshVertexBuffer

object Chunks {

    @JvmStatic
    fun vertex(x: Int, y: Int, z: Int, blockId: Int, direction: Int, light: Int, ao: Boolean) =
        object : ChunkVertex {
            override val x = x
            override val y = y
            override val z = z
            override val blockId = blockId
            override val direction = direction
            override val light = light
            override val ao = ao
        }

    @JvmStatic
    fun meshPrototype() = ChunkMeshPrototype(MeshVertexBuffer.createStatic())
}