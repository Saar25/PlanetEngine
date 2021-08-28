package org.saar.lwjgl.opengl.objects.attributes

import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL33

class InstancedAttribute(private val linker: AttributeLinker,
                         private val attributeIndex: Int,
                         private val instances: Int) : IAttribute {

    override fun enable() {
        GL20.glEnableVertexAttribArray(attributeIndex)
    }

    override fun disable() {
        GL20.glDisableVertexAttribArray(attributeIndex)
    }

    override fun link(stride: Int, offset: Int) {
        linker.link(attributeIndex, stride, offset)
        GL33.glVertexAttribDivisor(attributeIndex, instances)
    }

    override val bytesPerVertex: Int get() = linker.bytes
}