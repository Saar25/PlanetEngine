package org.saar.core.mesh.builder

import org.saar.core.mesh.buffer.MeshBuffer
import org.saar.core.mesh.buffer.MeshDataBuffer
import org.saar.lwjgl.opengl.attribute.IAttribute
import org.saar.lwjgl.opengl.vbo.Vbo
import org.saar.lwjgl.opengl.vbo.VboTarget
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.DataWriter
import org.saar.lwjgl.util.buffer.BufferBuilder

class MeshBufferBuilder(private val bufferBuilder: BufferBuilder, private val usage: VboUsage) {

    private val attributes = mutableListOf<IAttribute>()

    fun addAttribute(attribute: IAttribute) = this.attributes.add(attribute)

    fun addAttributes(attributes: Iterable<IAttribute>) = this.attributes.addAll(attributes)

    fun addAttributes(vararg attributes: IAttribute) = this.attributes.addAll(attributes)

    val writer: DataWriter = this.bufferBuilder.writer

    fun build(target: VboTarget): MeshBuffer {
        val vbo = Vbo.create(target, this.usage)
        val buffer = this.bufferBuilder.build()
        return MeshDataBuffer(vbo, buffer, this.attributes)
    }

    fun delete() = this.bufferBuilder.delete()
}