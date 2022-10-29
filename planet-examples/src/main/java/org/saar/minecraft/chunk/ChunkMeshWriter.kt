package org.saar.minecraft.chunk

import org.saar.core.mesh.writer.VertexMeshWriter
import org.saar.lwjgl.util.DataWriter

class ChunkMeshWriter(private val dataWriter: DataWriter) : VertexMeshWriter<ChunkVertex> {

    private fun Boolean.toInt() = if (this) 1 else 0

    override fun writeVertex(vertex: ChunkVertex) {
        val data = (vertex.x and 15 shl 28) or
                (vertex.z and 15 shl 24) or
                (vertex.y and 0xFF shl 16) or
                (vertex.blockId and 0xFF shl 8) or
                (vertex.direction and 7 shl 5) or
                (vertex.light and 0x0F shl 1) or
                (vertex.ao.toInt() and 1)
        this.dataWriter.writeInt(data)
    }
}