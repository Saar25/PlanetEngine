package org.saar.lwjgl.opengl.attribute.pointer

import org.lwjgl.opengl.GL30
import org.saar.lwjgl.opengl.constants.DataType

class IntegerAttributePointer(
    private val componentCount: Int,
    private val componentType: DataType,
) : AttributePointer {

    override fun point(index: Int, stride: Int, offset: Int) =
        GL30.glVertexAttribIPointer(index, this.componentCount,
            this.componentType.get(), stride, offset.toLong())

    override val bytesPerVertex get() = this.componentCount * this.componentType.bytes
}