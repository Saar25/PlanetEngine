package org.saar.core.mesh.buffer

import org.saar.lwjgl.opengl.attribute.IAttribute
import org.saar.lwjgl.opengl.vao.WritableVao
import org.saar.lwjgl.opengl.vbo.IVbo
import org.saar.lwjgl.util.buffer.LwjglBuffer

class MeshBuffer(
    private val vbo: IVbo,
    private val buffer: LwjglBuffer,
    private val attributes: Collection<IAttribute>,
) {
    fun store(offset: Long) {
        this.vbo.allocate(this.buffer.flip().limit().toLong())
        this.vbo.store(offset, this.buffer.asByteBuffer())
    }

    fun loadInVao(vao: WritableVao) = vao.loadVbo(this.vbo, *this.attributes.toTypedArray())

    fun delete() = this.buffer.close()
}