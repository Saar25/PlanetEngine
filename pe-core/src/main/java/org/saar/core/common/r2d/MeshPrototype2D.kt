package org.saar.core.common.r2d

import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.core.mesh.prototype.IndexedVertexMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class MeshPrototype2D(
    private val positionBuffer: MeshVertexBuffer,
    private val colourBuffer: MeshVertexBuffer,
    private val indexBuffer: MeshIndexBuffer,
) : IndexedVertexMeshPrototype<Vertex2D> {

    constructor(vertexBuffer: MeshVertexBuffer, indexBuffer: MeshIndexBuffer) : this(
        positionBuffer = vertexBuffer,
        colourBuffer = vertexBuffer,
        indexBuffer = indexBuffer,
    )

    init {
        this.positionBuffer.addAttribute(
            Attributes.of(0, 2, DataType.FLOAT, false))
        this.colourBuffer.addAttribute(
            Attributes.of(1, 3, DataType.FLOAT, false))
    }

    override val vertexBuffers = arrayOf(this.positionBuffer, this.colourBuffer).distinct()

    override val indexBuffers = listOf(this.indexBuffer)

    override fun writeVertex(vertex: Vertex2D) {
        this.positionBuffer.writer.write2f(vertex.position2f)
        this.colourBuffer.writer.write3f(vertex.colour3f)
    }

    override fun readVertex(): Vertex2D {
        val position = this.positionBuffer.reader.read2f()
        val colour = this.colourBuffer.reader.read3f()
        return R2D.vertex(position, colour)
    }

    override fun writeIndex(index: Int) {
        this.indexBuffer.writer.writeInt(index)
    }

    override fun readIndex(): Int {
        return this.indexBuffer.reader.readInt()
    }
}