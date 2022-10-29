package org.saar.core.mesh.buffer

import org.saar.lwjgl.opengl.attribute.IAttribute
import org.saar.lwjgl.opengl.vbo.Vbo
import org.saar.lwjgl.opengl.vbo.VboTarget
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.DataWriter
import org.saar.lwjgl.util.buffer.BufferBuilder

class DataMeshBufferBuilder(
    private val bufferBuilder: BufferBuilder,
    private val usage: VboUsage,
) : MeshBufferBuilder {

    private val attributes = mutableListOf<IAttribute>()

    fun addAttribute(attribute: IAttribute) = this.attributes.add(attribute)

    fun addAttributes(attributes: Iterable<IAttribute>) = this.attributes.addAll(attributes)

    fun addAttributes(vararg attributes: IAttribute) = this.attributes.addAll(attributes)

    override val writer: DataWriter = this.bufferBuilder.writer

    override fun build(target: VboTarget): DataMeshBuffer {
        val vbo = Vbo.create(target, this.usage)
        val buffer = this.bufferBuilder.build()
        return DataMeshBuffer(vbo, buffer, this.attributes)
    }

    override fun delete() = this.bufferBuilder.delete()
}