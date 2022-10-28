package org.saar.lwjgl.opengl.attribute.pointer

import org.lwjgl.opengl.GL20
import org.saar.lwjgl.opengl.constants.DataType

class FloatAttributePointer(
    private val componentCount: Int,
    private val componentType: DataType,
    private val normalized: Boolean,
) : AttributePointer {

    override fun point(index: Int, stride: Int, offset: Int) =
        GL20.glVertexAttribPointer(index, this.componentCount,
            this.componentType.get(), this.normalized, stride, offset.toLong())

    override val bytesPerVertex get() = this.componentCount * this.componentType.bytes
}