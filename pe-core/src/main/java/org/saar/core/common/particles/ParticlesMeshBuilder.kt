package org.saar.core.common.particles

import org.saar.core.mesh.DrawCallMesh
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.builder.MeshBufferBuilder
import org.saar.core.mesh.builder.MeshBuilder
import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.lwjgl.opengl.vbo.VboTarget

class ParticlesMeshBuilder(
    private val instances: Int,
    private val positionBufferBuilder: MeshBufferBuilder,
    private val birthBufferBuilder: MeshBufferBuilder,
) : MeshBuilder {

    val writer = ParticlesMeshWriter(
        this.positionBufferBuilder.writer,
        this.birthBufferBuilder.writer,
    )

    private val bufferBuilders = listOf(
        this.positionBufferBuilder,
        this.birthBufferBuilder,
    ).distinct()

    init {

        this.positionBufferBuilder.addAttribute(
            Attributes.ofInstanced(0, 3, DataType.FLOAT, false))
        this.birthBufferBuilder.addAttribute(
            Attributes.ofIntegerInstanced(1, 1, DataType.INT))
    }

    override fun delete() = this.bufferBuilders.forEach { it.delete() }

    override fun load(): Mesh {
        val vao = Vao.create()

        val buffers = this.bufferBuilders.map { it.build(VboTarget.ARRAY_BUFFER) }

        buffers.forEach {
            it.store(0)
            it.loadInVao(vao)
        }

        val drawCall = InstancedArraysDrawCall(RenderMode.TRIANGLES, 0, 4, this.instances)
        return DrawCallMesh(vao, drawCall)
    }
}