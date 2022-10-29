package org.saar.core.common.normalmap

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

class NormalMappedMeshBuilder(
    private val indices: Int,
    private val positionBufferBuilder: DataMeshBufferBuilder,
    private val uvCoordBufferBuilder: DataMeshBufferBuilder,
    private val normalBufferBuilder: DataMeshBufferBuilder,
    private val tangentBufferBuilder: DataMeshBufferBuilder,
    private val biTangentBufferBuilder: DataMeshBufferBuilder,
    private val indexBufferBuilder: IndexMeshBufferBuilder,
) : MeshBuilder {

    val writer = NormalMappedMeshWriter(
        this.positionBufferBuilder.writer,
        this.uvCoordBufferBuilder.writer,
        this.normalBufferBuilder.writer,
        this.tangentBufferBuilder.writer,
        this.biTangentBufferBuilder.writer,
        this.indexBufferBuilder.writer,
    )

    private val bufferBuilders = listOf(
        this.positionBufferBuilder,
        this.uvCoordBufferBuilder,
        this.normalBufferBuilder,
        this.tangentBufferBuilder,
        this.biTangentBufferBuilder,
        this.indexBufferBuilder,
    ).distinct()

    private val vertexBufferBuilders = listOf(
        this.positionBufferBuilder,
        this.uvCoordBufferBuilder,
        this.normalBufferBuilder,
        this.tangentBufferBuilder,
        this.biTangentBufferBuilder,
    ).distinct()

    init {
        this.positionBufferBuilder.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, false))
        this.uvCoordBufferBuilder.addAttribute(
            Attributes.of(1, 2, DataType.FLOAT, false))
        this.normalBufferBuilder.addAttribute(
            Attributes.of(2, 3, DataType.FLOAT, false))
        this.tangentBufferBuilder.addAttribute(
            Attributes.of(3, 3, DataType.FLOAT, false))
        this.biTangentBufferBuilder.addAttribute(
            Attributes.of(4, 3, DataType.FLOAT, false))
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