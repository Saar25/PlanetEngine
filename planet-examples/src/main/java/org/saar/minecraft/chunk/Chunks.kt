package org.saar.minecraft.chunk

import org.saar.core.mesh.build.MeshBufferProperty
import org.saar.core.mesh.build.buffers.MeshVertexBuffer

object Chunks {

    @JvmStatic
    fun vertex(x: Int, y: Int, z: Int, id: Int, direction: Int): ChunkVertex {
        return object : ChunkVertex {
            override val x = x
            override val y = y
            override val z = z
            override val blockId = id
            override val direction = direction
        }
    }

    @JvmStatic
    fun meshPrototype(dataBuffer: MeshVertexBuffer): ChunkMeshPrototype {
        return object : ChunkMeshPrototype {
            @MeshBufferProperty
            override val dataBuffer = dataBuffer
        }
    }

    @JvmStatic
    fun meshPrototype(): ChunkMeshPrototype {
        return meshPrototype(MeshVertexBuffer.createStatic())
    }
}