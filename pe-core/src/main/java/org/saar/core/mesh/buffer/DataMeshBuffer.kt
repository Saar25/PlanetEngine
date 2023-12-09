package org.saar.core.mesh.buffer

import org.saar.lwjgl.opengl.attribute.AttributeComposite
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
        val bytes = Attributes.sumBytes(this.attributes)
        this.vbo.allocate(this.buffer.clear().capacity().toLong())
        this.vbo.store(offset * bytes, this.buffer.asByteBuffer())
    }

    override fun loadInVao(vao: WritableVao) = vao.loadVbo(this.vbo, AttributeComposite(this.attributes))

    override fun delete() = this.buffer.close()
}