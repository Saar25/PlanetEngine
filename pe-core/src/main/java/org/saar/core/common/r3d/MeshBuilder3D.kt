package org.saar.core.common.r3d

import org.saar.core.mesh.DrawCallMesh
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.MeshBuilder
import org.saar.core.mesh.buffer.DataMeshBufferBuilder
import org.saar.core.mesh.buffer.IndexMeshBufferBuilder
import org.saar.lwjgl.opengl.attribute.AttributeComposite
import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.InstancedElementsDrawCall
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.lwjgl.opengl.vbo.VboTarget

class MeshBuilder3D(
    private val indices: Int,
    private val instances: Int,
    private val positionBufferBuilder: DataMeshBufferBuilder,
    private val normalBufferBuilder: DataMeshBufferBuilder,
    private val colourBufferBuilder: DataMeshBufferBuilder,
    private val transformBufferBuilder: DataMeshBufferBuilder,
    private val indexBufferBuilder: IndexMeshBufferBuilder,
) : MeshBuilder {

    val writer = MeshWriter3D(
        this.positionBufferBuilder.writer,
        this.normalBufferBuilder.writer,
        this.colourBufferBuilder.writer,
        this.transformBufferBuilder.writer,
        this.indexBufferBuilder.writer,
    )

    private val bufferBuilders = listOf(
        this.positionBufferBuilder,
        this.normalBufferBuilder,
        this.colourBufferBuilder,
        this.transformBufferBuilder,
        this.indexBufferBuilder,
    ).distinct()

    private val vertexBufferBuilders = listOf(
        this.positionBufferBuilder,
        this.normalBufferBuilder,
        this.colourBufferBuilder,
    ).distinct()

    init {
        this.positionBufferBuilder.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, true))
        this.normalBufferBuilder.addAttribute(
            Attributes.of(1, 3, DataType.FLOAT, true))
        this.colourBufferBuilder.addAttribute(
            Attributes.of(2, 3, DataType.FLOAT, true))
        this.transformBufferBuilder.addAttribute(AttributeComposite(
            Attributes.ofInstanced(3, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(4, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(5, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(6, 4, DataType.FLOAT, false)
        ))
    }

    override fun delete() = this.bufferBuilders.forEach { it.delete() }

    override fun load(): Mesh {
        val vao = Vao.create()

        val buffers = this.vertexBufferBuilders.map { it.build(VboTarget.ARRAY_BUFFER) } +
                this.transformBufferBuilder.build(VboTarget.ARRAY_BUFFER) +
                this.indexBufferBuilder.build(VboTarget.ELEMENT_ARRAY_BUFFER)

        buffers.forEach {
            it.store(0)
            it.loadInVao(vao)
        }

        val drawCall = InstancedElementsDrawCall(RenderMode.TRIANGLES,
            this.indices, DataType.U_INT, this.instances)
        return DrawCallMesh(vao, drawCall)
    }
}