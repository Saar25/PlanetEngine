package org.saar.core.common.texture3d

import org.saar.core.mesh.DrawCallMesh
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.MeshBuilder
import org.saar.core.mesh.buffer.DataMeshBufferBuilder
import org.saar.core.mesh.buffer.IndexMeshBufferBuilder
import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.lwjgl.opengl.vbo.VboTarget

class Texture3DMeshBuilder(
    private val indices: Int,
    private val positionBufferBuilder: DataMeshBufferBuilder,
    private val uvCoordBufferBuilder: DataMeshBufferBuilder,
    private val indexBufferBuilder: IndexMeshBufferBuilder,
) : MeshBuilder {

    val writer = Texture3DMeshWriter(
        this.positionBufferBuilder.writer,
        this.uvCoordBufferBuilder.writer,
        this.indexBufferBuilder.writer,
    )

    private val bufferBuilders = listOf(
        this.positionBufferBuilder,
        this.uvCoordBufferBuilder,
        this.indexBufferBuilder,
    ).distinct()

    private val vertexBufferBuilders = listOf(
        this.positionBufferBuilder,
        this.uvCoordBufferBuilder,
    ).distinct()

    init {
        this.positionBufferBuilder.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, true))
        this.uvCoordBufferBuilder.addAttribute(
            Attributes.of(1, 2, DataType.FLOAT, true))
    }

    override fun delete() = this.bufferBuilders.forEach { it.delete() }

    override fun load(): Mesh {
        val vao = Vao.create()

        val buffers = this.vertexBufferBuilders.map { it.build(VboTarget.ARRAY_BUFFER) } +
                this.indexBufferBuilder.build(VboTarget.ELEMENT_ARRAY_BUFFER)

        buffers.forEach {
            it.store(0)
            it.loadInVao(vao)
        }

        val drawCall = ElementsDrawCall(RenderMode.TRIANGLES, this.indices, DataType.U_INT)
        return DrawCallMesh(vao, drawCall)
    }
}