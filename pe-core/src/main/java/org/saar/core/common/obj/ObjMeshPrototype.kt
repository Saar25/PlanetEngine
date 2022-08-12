package org.saar.core.common.obj

import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.core.mesh.prototype.IndexedVertexMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.Attributes

class ObjMeshPrototype(
    private val positionBuffer: MeshVertexBuffer,
    private val uvCoordBuffer: MeshVertexBuffer,
    private val normalBuffer: MeshVertexBuffer,
    private val indexBuffer: MeshIndexBuffer,
) : IndexedVertexMeshPrototype<ObjVertex> {

    constructor(vertexBuffer: MeshVertexBuffer, indexBuffer: MeshIndexBuffer) : this(
        positionBuffer = vertexBuffer,
        uvCoordBuffer = vertexBuffer,
        normalBuffer = vertexBuffer,
        indexBuffer = indexBuffer,
    )

    init {
        this.positionBuffer.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, false))
        this.uvCoordBuffer.addAttribute(
            Attributes.of(1, 2, DataType.FLOAT, false))
        this.normalBuffer.addAttribute(
            Attributes.of(2, 3, DataType.FLOAT, false))
    }

    override val vertexBuffers = arrayOf(this.positionBuffer, this.uvCoordBuffer, this.normalBuffer).distinct()

    override val indexBuffers = listOf(this.indexBuffer)

    override fun writeVertex(vertex: ObjVertex) {
        this.positionBuffer.writer.write3f(vertex.position3f)
        this.uvCoordBuffer.writer.write2f(vertex.uvCoord2f)
        this.normalBuffer.writer.write3f(vertex.normal3f)
    }

    override fun readVertex(): ObjVertex {
        val position = this.positionBuffer.reader.read3f()
        val uvCoord = this.uvCoordBuffer.reader.read2f()
        val normal = this.normalBuffer.reader.read3f()
        return Obj.vertex(position, uvCoord, normal)
    }

    override fun writeIndex(index: Int) {
        this.indexBuffer.writer.writeInt(index)
    }

    override fun readIndex(): Int {
        return this.indexBuffer.reader.readInt()
    }
}