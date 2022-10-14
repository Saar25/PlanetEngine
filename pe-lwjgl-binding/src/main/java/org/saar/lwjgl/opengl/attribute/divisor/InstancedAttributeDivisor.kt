package org.saar.lwjgl.opengl.attribute.divisor

import org.lwjgl.opengl.GL33

class InstancedAttributeDivisor(private val instances: Int) : AttributeDivisor {

    override fun divide(index: Int) = GL33.glVertexAttribDivisor(index, this.instances)

}