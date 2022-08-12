package org.saar.core.mesh.writer

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.pointer.AttributePointer
import org.saar.lwjgl.opengl.attribute.pointer.FloatAttributePointer
import org.saar.lwjgl.util.DataWriter

class FloatVertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    val attributePointer: AttributePointer =
        FloatAttributePointer(1, DataType.FLOAT, false)

    override val attributePointers: List<AttributePointer> = listOf(this.attributePointer)

    fun write(value: Float) = this.dataWriter.writeFloat(value)
}