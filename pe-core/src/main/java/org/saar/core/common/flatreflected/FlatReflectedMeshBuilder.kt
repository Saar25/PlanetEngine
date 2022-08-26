package org.saar.core.common.flatreflected

import org.saar.core.mesh.DrawCallMesh
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.buffer.MeshBufferBuilder
import org.saar.core.mesh.MeshBuilder
import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.lwjgl.opengl.vbo.VboTarget

class FlatReflectedMeshBuilder(
    private val indices: Int,
    private val positionBufferBuilder: MeshBufferBuilder,
    private val indexBufferBuilder: MeshBufferBuilder,
) : MeshBuilder {

    val writer = FlatReflectedMeshWriter(
        this.positionBufferBuilder.writer,
        this.indexBufferBuilder.writer,
    )

    private val bufferBuilders = listOf(
        this.positionBufferBuilder,
        this.indexBufferBuilder,
    ).distinct()

    init {
        this.positionBufferBuilder.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, false))
    }

    override fun delete() = this.bufferBuilders.forEach { it.delete() }

    override fun load(): Mesh {
        val vao = Vao.create()

        val buffers = listOf(
            this.positionBufferBuilder.build(VboTarget.ARRAY_BUFFER),
            this.indexBufferBuilder.build(VboTarget.ELEMENT_ARRAY_BUFFER)
        )

        buffers.forEach {
            it.store(0)
            it.loadInVao(vao)
        }

        val drawCall = ElementsDrawCall(RenderMode.TRIANGLES, this.indices, DataType.U_INT)
        return DrawCallMesh(vao, drawCall)
    }
}