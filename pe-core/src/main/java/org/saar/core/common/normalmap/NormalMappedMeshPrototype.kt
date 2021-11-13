package org.saar.core.common.normalmap

import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.core.mesh.prototype.IndexedVertexMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class NormalMappedMeshPrototype(
    private val positionBuffer: MeshVertexBuffer,
    private val uvCoordBuffer: MeshVertexBuffer,
    private val normalBuffer: MeshVertexBuffer,
    private val tangentBuffer: MeshVertexBuffer,
    private val biTangentBuffer: MeshVertexBuffer,
    private val indexBuffer: MeshIndexBuffer,
) : IndexedVertexMeshPrototype<NormalMappedVertex> {

    constructor(vertexBuffer: MeshVertexBuffer, indexBuffer: MeshIndexBuffer) : this(
        positionBuffer = vertexBuffer,
        uvCoordBuffer = vertexBuffer,
        normalBuffer = vertexBuffer,
        tangentBuffer = vertexBuffer,
        biTangentBuffer = vertexBuffer,
        indexBuffer = indexBuffer,
    )

    init {
        this.positionBuffer.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, false))
        this.uvCoordBuffer.addAttribute(
            Attributes.of(1, 2, DataType.FLOAT, false))
        this.normalBuffer.addAttribute(
            Attributes.of(2, 3, DataType.FLOAT, false))
        this.tangentBuffer.addAttribute(
            Attributes.of(3, 3, DataType.FLOAT, false))
        this.biTangentBuffer.addAttribute(
            Attributes.of(4, 3, DataType.FLOAT, false))
    }

    override val vertexBuffers = arrayOf(
        this.positionBuffer,
        this.uvCoordBuffer,
        this.normalBuffer,
        this.tangentBuffer,
        this.biTangentBuffer,
    ).distinct()

    override val indexBuffers = listOf(this.indexBuffer)

    override fun writeVertex(vertex: NormalMappedVertex) {
        this.positionBuffer.writer.write3f(vertex.position3f)
        this.uvCoordBuffer.writer.write2f(vertex.uvCoord2f)
        this.normalBuffer.writer.write3f(vertex.normal3f)
        this.tangentBuffer.writer.write3f(vertex.tangent3f)
        this.biTangentBuffer.writer.write3f(vertex.biTangent3f)
    }

    override fun readVertex(): NormalMappedVertex {
        val position = this.positionBuffer.reader.read3f()
        val uvCoord = this.uvCoordBuffer.reader.read2f()
        val normal = this.normalBuffer.reader.read3f()
        val tangent = this.tangentBuffer.reader.read3f()
        val biTangent = this.biTangentBuffer.reader.read3f()
        return NormalMapped.vertex(position, uvCoord, normal, tangent, biTangent)
    }

    override fun writeIndex(index: Int) {
        this.indexBuffer.writer.writeInt(index)
    }

    override fun readIndex(): Int {
        return this.indexBuffer.reader.readInt()
    }
}