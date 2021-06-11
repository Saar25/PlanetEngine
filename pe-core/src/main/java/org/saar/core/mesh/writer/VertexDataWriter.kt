package org.saar.core.mesh.writer

import org.saar.lwjgl.opengl.objects.attributes.Attribute
import org.saar.lwjgl.opengl.objects.attributes.AttributeLinker

interface VertexDataWriter {

    val attributeLinkers: List<AttributeLinker>

    fun toAttributes(index: Int): List<Attribute> {
        return this.attributeLinkers.mapIndexed { i, linker -> Attribute(linker, index + i) }
    }

    fun toInstanceAttributes(index: Int, instances: Int): List<Attribute> {
        return this.attributeLinkers.mapIndexed { i, linker -> Attribute(linker, index + i, instances) }
    }
}