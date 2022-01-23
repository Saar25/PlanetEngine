package org.saar.minecraft.chunk

import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.core.mesh.prototype.VertexMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class ChunkMeshPrototype(
    private val dataBuffer: MeshVertexBuffer,
) : VertexMeshPrototype<ChunkVertex> {

    private fun Boolean.toInt() = if (this) 1 else 0

    override val vertexBuffers = listOf(this.dataBuffer)

    init {
        this.dataBuffer.addAttribute(
            Attributes.ofInteger(0, 1, DataType.U_INT))
    }

    override fun writeVertex(vertex: ChunkVertex) {
        val data = (vertex.x and 15 shl 28) or
                (vertex.z and 15 shl 24) or
                (vertex.y and 0xFF shl 16) or
                (vertex.blockId and 0xFF shl 8) or
                (vertex.vertexId and 7 shl 5) or
                (vertex.textureInc.toInt() shl 4) or
                (vertex.shadow and 15)
        this.dataBuffer.writer.writeInt(data)
    }

    override fun readVertex(): ChunkVertex {
        val data = this.dataBuffer.reader.readInt()
        return Chunks.vertex(
            x = data shr 28 and 15,
            z = data shr 24 and 15,
            y = data shr 16 and 0xFF,
            blockId = data shr 8 and 0xFF,
            vertexId = data shr 5 and 7,
            textureInc = data shr 4 and 1 == 1,
            shadow = data and 15
        )
    }
}