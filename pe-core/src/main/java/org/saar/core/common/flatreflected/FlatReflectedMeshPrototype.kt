package org.saar.core.common.flatreflected

import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.core.mesh.prototype.IndexedVertexMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class FlatReflectedMeshPrototype(
    private val positionBuffer: MeshVertexBuffer,
    private val indexBuffer: MeshIndexBuffer,
) : IndexedVertexMeshPrototype<FlatReflectedVertex> {

    init {
        this.positionBuffer.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, false))
    }

    override val vertexBuffers = listOf(this.positionBuffer)

    override val indexBuffers = listOf(this.indexBuffer)

    override fun writeVertex(vertex: FlatReflectedVertex) {
        this.positionBuffer.writer.write3f(vertex.position3f)
    }

    override fun readVertex(): FlatReflectedVertex {
        val position = this.positionBuffer.reader.read3f()
        return FlatReflected.vertex(position)
    }

    override fun writeIndex(index: Int) {
        this.indexBuffer.writer.writeInt(index)
    }

    override fun readIndex(): Int {
        return this.indexBuffer.reader.readInt()
    }
}