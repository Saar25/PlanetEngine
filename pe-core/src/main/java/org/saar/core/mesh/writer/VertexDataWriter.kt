package org.saar.core.mesh.writer

import org.saar.lwjgl.opengl.objects.attributes.Attribute
import org.saar.lwjgl.opengl.objects.attributes.AttributeLinker
import org.saar.lwjgl.opengl.objects.attributes.IAttribute
import org.saar.lwjgl.opengl.objects.attributes.InstancedAttribute

interface VertexDataWriter {

    val attributeLinkers: List<AttributeLinker>

    fun toAttributes(index: Int): List<IAttribute> {
        return this.attributeLinkers.mapIndexed { i, linker -> Attribute(linker, index + i) }
    }

    fun toInstanceAttributes(index: Int, instances: Int): List<IAttribute> {
        return this.attributeLinkers.mapIndexed { i, linker -> InstancedAttribute(linker, index + i, instances) }
    }
}