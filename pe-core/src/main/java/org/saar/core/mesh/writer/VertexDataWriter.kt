package org.saar.core.mesh.writer

import org.saar.lwjgl.opengl.attribute.Attribute
import org.saar.lwjgl.opengl.attribute.AttributeLinker
import org.saar.lwjgl.opengl.attribute.IAttribute
import org.saar.lwjgl.opengl.attribute.InstancedAttribute

interface VertexDataWriter {

    val attributeLinkers: List<AttributeLinker>

    fun toAttributes(index: Int): List<IAttribute> {
        return this.attributeLinkers.mapIndexed { i, linker -> Attribute(linker, index + i) }
    }

    fun toInstanceAttributes(index: Int, instances: Int): List<IAttribute> {
        return this.attributeLinkers.mapIndexed { i, linker -> InstancedAttribute(linker, index + i, instances) }
    }
}