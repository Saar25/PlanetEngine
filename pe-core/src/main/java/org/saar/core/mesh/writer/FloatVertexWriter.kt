package org.saar.core.mesh.writer

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.AttributeLinker
import org.saar.lwjgl.opengl.objects.attributes.FloatAttributeLinker
import org.saar.lwjgl.util.DataWriter

class FloatVertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    val attributeLinker: AttributeLinker = FloatAttributeLinker(1, DataType.FLOAT, false)

    override val attributeLinkers: List<AttributeLinker> = listOf(this.attributeLinker)

    fun write(value: Float) = this.dataWriter.writeFloat(value)
}