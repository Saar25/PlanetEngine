package org.saar.lwjgl.opengl.attribute

import org.lwjgl.opengl.GL20
import org.saar.lwjgl.opengl.attribute.divisor.AttributeDivisor
import org.saar.lwjgl.opengl.attribute.pointer.AttributePointer

class Attribute(
    private val index: Int,
    private val linker: AttributePointer,
    private val divisor: AttributeDivisor,
) : IAttribute {

    override fun enable() = GL20.glEnableVertexAttribArray(this.index)

    override fun disable() = GL20.glDisableVertexAttribArray(this.index)

    override fun link(stride: Int, offset: Int) {
        this.linker.point(this.index, stride, offset)
        this.divisor.divide(this.index)
    }

    override val bytesPerVertex get() = this.linker.bytesPerVertex
}