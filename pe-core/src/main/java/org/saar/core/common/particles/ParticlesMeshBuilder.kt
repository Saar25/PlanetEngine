package org.saar.core.common.particles

import org.saar.core.mesh.MeshBuilder
import org.saar.core.mesh.buffer.DataMeshBufferBuilder
import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.lwjgl.opengl.vbo.VboTarget

class ParticlesMeshBuilder(
    private val instances: Int,
    private val positionBufferBuilder: DataMeshBufferBuilder,
    private val birthBufferBuilder: DataMeshBufferBuilder,
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

    override fun load(): ParticlesMesh {
        val vao = Vao.create()

        val buffers = this.bufferBuilders.associateWith { it.build(VboTarget.ARRAY_BUFFER) }

        buffers.forEach {
            it.value.store(0)
            it.value.loadInVao(vao)
        }

        val meshBuffers = ParticlesMeshBuffers(
            buffers[this.positionBufferBuilder]!!,
            buffers[this.birthBufferBuilder]!!)

        val drawCall = InstancedArraysDrawCall(RenderMode.TRIANGLE_STRIP, 0, 4, this.instances)
        return ParticlesMesh(vao, drawCall, meshBuffers)
    }
}