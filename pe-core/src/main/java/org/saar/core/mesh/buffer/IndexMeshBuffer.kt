package org.saar.core.mesh.buffer

import org.saar.lwjgl.opengl.vao.WritableVao
import org.saar.lwjgl.opengl.vbo.IVbo
import org.saar.lwjgl.util.buffer.BufferReader
import org.saar.lwjgl.util.buffer.BufferWriter
import org.saar.lwjgl.util.buffer.LwjglBuffer

class IndexMeshBuffer(
    private val vbo: IVbo,
    private val buffer: LwjglBuffer,
) : MeshBuffer {
    override val writer: BufferWriter = this.buffer.writer

    override val reader: BufferReader = this.buffer.reader

    override fun store(offset: Long) {
        this.vbo.allocate(this.buffer.flip().limit().toLong())
        this.vbo.store(offset, this.buffer.asByteBuffer())
    }

    override fun loadInVao(vao: WritableVao) = vao.loadVbo(this.vbo)

    override fun delete() = this.buffer.close()
}