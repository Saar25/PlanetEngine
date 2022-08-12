package org.saar.core.mesh.writer

import org.saar.lwjgl.opengl.attribute.*
import org.saar.lwjgl.opengl.attribute.divisor.AttributeDivisor
import org.saar.lwjgl.opengl.attribute.divisor.InstancedAttributeDivisor
import org.saar.lwjgl.opengl.attribute.divisor.NoAttributeDivisor
import org.saar.lwjgl.opengl.attribute.pointer.AttributePointer

interface VertexDataWriter {

    val attributePointers: List<AttributePointer>

    fun toAttributes(index: Int) = toAttributes(index, NoAttributeDivisor())

    fun toInstanceAttributes(index: Int, instances: Int) = toAttributes(index, InstancedAttributeDivisor(instances))

    private fun toAttributes(index: Int, divisor: AttributeDivisor) =
        this.attributePointers.mapIndexed { i, linker -> Attribute(index + i, linker, divisor) }
}