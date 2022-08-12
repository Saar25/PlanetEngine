package org.saar.core.mesh.writer

import org.joml.Matrix4fc
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.pointer.AttributePointer
import org.saar.lwjgl.opengl.attribute.pointer.FloatAttributePointer
import org.saar.lwjgl.util.DataWriter

class Mat4VertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    override val attributePointers: List<AttributePointer> = listOf(
        FloatAttributePointer(4, DataType.FLOAT, false),
        FloatAttributePointer(4, DataType.FLOAT, false),
        FloatAttributePointer(4, DataType.FLOAT, false),
        FloatAttributePointer(4, DataType.FLOAT, false)
    )

    fun write(value: Matrix4fc) = this.dataWriter.write4x4f(value)
}