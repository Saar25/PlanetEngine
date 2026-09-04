package org.saar.minecraft.chunk

import org.saar.core.mesh.buffer.DataMeshBufferBuilder
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder

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
    fun meshBuilder(vertices: Int): ChunkMeshBuilder {
        val bufferBuilder = FixedBufferBuilder(vertices * DataType.INT.bytes)
        val meshBufferBuilder = DataMeshBufferBuilder(bufferBuilder, VboUsage.STATIC_DRAW)
        return ChunkMeshBuilder(vertices, meshBufferBuilder)
    }
}