package org.saar.core.common.r3d

import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshInstanceBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.core.mesh.prototype.InstancedIndexedVertexMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.maths.transform.SimpleTransform

class MeshPrototype3D(
    private val positionBuffer: MeshVertexBuffer,
    private val normalBuffer: MeshVertexBuffer,
    private val colourBuffer: MeshVertexBuffer,
    private val transformBuffer: MeshInstanceBuffer,
    private val indexBuffer: MeshIndexBuffer,
) : InstancedIndexedVertexMeshPrototype<Vertex3D, Instance3D> {

    constructor(
        vertexBuffer: MeshVertexBuffer,
        instanceBuffer: MeshInstanceBuffer,
        indexBuffer: MeshIndexBuffer,
    ) : this(
        positionBuffer = vertexBuffer,
        normalBuffer = vertexBuffer,
        colourBuffer = vertexBuffer,
        transformBuffer = instanceBuffer,
        indexBuffer = indexBuffer,
    )

    init {
        this.positionBuffer.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, true))
        this.normalBuffer.addAttribute(
            Attributes.of(1, 3, DataType.FLOAT, true))
        this.colourBuffer.addAttribute(
            Attributes.of(2, 3, DataType.FLOAT, true))
        this.transformBuffer.addAttributes(
            Attributes.ofInstanced(3, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(4, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(5, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(6, 4, DataType.FLOAT, false)
        )
    }

    override val vertexBuffers = arrayOf(
        this.positionBuffer,
        this.normalBuffer,
        this.colourBuffer,
    ).distinct()

    override val instanceBuffers = listOf(this.transformBuffer)

    override val indexBuffers = listOf(this.indexBuffer)

    override fun writeVertex(vertex: Vertex3D) {
        this.positionBuffer.writer.write3f(vertex.position3f)
        this.normalBuffer.writer.write3f(vertex.normal3f)
        this.colourBuffer.writer.write3f(vertex.colour3f)
    }

    override fun readVertex(): Vertex3D {
        val position = this.positionBuffer.reader.read3f()
        val normal = this.normalBuffer.reader.read3f()
        val colour = this.colourBuffer.reader.read3f()
        return R3D.vertex(position, normal, colour)
    }

    override fun writeInstance(instance: Instance3D) {
        this.transformBuffer.writer.write4x4f(instance.transform.transformationMatrix)
    }

    override fun readInstance(): Instance3D {
        val transform = this.transformBuffer.reader.read4x4f()
        // TODO: implement correctly
        return R3D.instance(SimpleTransform())
    }

    override fun writeIndex(index: Int) {
        this.indexBuffer.writer.writeInt(index)
    }

    override fun readIndex(): Int {
        return this.indexBuffer.reader.readInt()
    }
}