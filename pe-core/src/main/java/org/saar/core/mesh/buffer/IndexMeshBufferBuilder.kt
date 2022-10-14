package org.saar.core.mesh.buffer

import org.saar.lwjgl.opengl.vbo.Vbo
import org.saar.lwjgl.opengl.vbo.VboTarget
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.DataWriter
import org.saar.lwjgl.util.buffer.BufferBuilder

class IndexMeshBufferBuilder(
    private val bufferBuilder: BufferBuilder,
    private val usage: VboUsage,
) : MeshBufferBuilder {

    override val writer: DataWriter = this.bufferBuilder.writer

    override fun build(target: VboTarget): MeshBuffer {
        val vbo = Vbo.create(target, this.usage)
        val buffer = this.bufferBuilder.build()
        return IndexMeshBuffer(vbo, buffer)
    }

    override fun delete() = this.bufferBuilder.delete()
}