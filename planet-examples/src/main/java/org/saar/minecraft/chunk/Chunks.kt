package org.saar.minecraft.chunk

import org.saar.core.mesh.buffer.MeshBufferProperty
import org.saar.core.mesh.buffer.MeshVertexBuffer

object Chunks {

    @JvmStatic
    fun vertex(x: Int, y: Int, z: Int, blockId: Int, vertexId: Int, textureInc: Boolean): ChunkVertex {
        return object : ChunkVertex {
            override val x = x
            override val y = y
            override val z = z
            override val blockId = blockId
            override val vertexId = vertexId
            override val textureInc = textureInc
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