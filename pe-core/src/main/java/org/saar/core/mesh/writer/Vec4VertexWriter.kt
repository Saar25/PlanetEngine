package org.saar.core.mesh.writer

import org.joml.Vector4fc
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.pointer.AttributePointer
import org.saar.lwjgl.opengl.attribute.pointer.FloatAttributePointer
import org.saar.lwjgl.util.DataWriter

class Vec4VertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    val attributePointer: AttributePointer =
        FloatAttributePointer(4, DataType.FLOAT, false)

    override val attributePointers: List<AttributePointer> = listOf(this.attributePointer)

    fun write(value: Vector4fc) {
        write(value.x(), value.y(), value.z(), value.w())
    }

    fun write(x: Float, y: Float, z: Float, w: Float) {
        this.dataWriter.writeFloat(x)
        this.dataWriter.writeFloat(y)
        this.dataWriter.writeFloat(z)
        this.dataWriter.writeFloat(w)
    }
}