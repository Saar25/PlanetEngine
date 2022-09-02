package org.saar.core.mesh.buffer

import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.lwjgl.opengl.attribute.IAttribute
import org.saar.lwjgl.opengl.vao.WritableVao
import org.saar.lwjgl.opengl.vbo.IVbo
import org.saar.lwjgl.util.buffer.BufferReader
import org.saar.lwjgl.util.buffer.BufferWriter
import org.saar.lwjgl.util.buffer.LwjglBuffer

class DataMeshBuffer(
    private val vbo: IVbo,
    private val buffer: LwjglBuffer,
    private val attributes: Collection<IAttribute>,
) : MeshBuffer {

    override val writer: BufferWriter = this.buffer.writer

    override val reader: BufferReader = this.buffer.reader

    fun offset(index: Int) {
        val bytes = Attributes.sumBytes(this.attributes)
        this.buffer.position(bytes * index)
    }

    override fun store(offset: Long) {
        this.vbo.allocate(this.buffer.flip().limit().toLong())
        this.vbo.store(offset, this.buffer.asByteBuffer())
    }

    override fun loadInVao(vao: WritableVao) = vao.loadVbo(this.vbo, *this.attributes.toTypedArray())

    override fun delete() = this.buffer.close()
}