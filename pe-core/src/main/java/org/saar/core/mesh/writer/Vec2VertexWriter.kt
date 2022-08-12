package org.saar.core.mesh.writer

import org.joml.Vector2fc
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.pointer.AttributePointer
import org.saar.lwjgl.opengl.attribute.pointer.FloatAttributePointer
import org.saar.lwjgl.util.DataWriter

class Vec2VertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    val attributePointer: AttributePointer =
        FloatAttributePointer(2, DataType.FLOAT, false)

    override val attributePointers: List<AttributePointer> = listOf(this.attributePointer)

    fun write(value: Vector2fc) {
        write(value.x(), value.y())
    }

    fun write(x: Float, y: Float) {
        this.dataWriter.writeFloat(x)
        this.dataWriter.writeFloat(y)
    }
}