package org.saar.core.mesh.writer

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.AttributeLinker
import org.saar.lwjgl.opengl.objects.attributes.FloatAttributeLinker
import org.saar.lwjgl.util.DataWriter

class FloatVertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    override val attributeLinkers: List<AttributeLinker> = listOf(
        FloatAttributeLinker(1, DataType.FLOAT, false)
    )

    fun write(value: Float) = this.dataWriter.writeFloat(value)
}