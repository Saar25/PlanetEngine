package org.saar.core.mesh.writer

import org.joml.Vector3fc
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.pointer.AttributePointer
import org.saar.lwjgl.opengl.attribute.pointer.FloatAttributePointer
import org.saar.lwjgl.util.DataWriter

class Vec3VertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    val attributePointer: AttributePointer =
        FloatAttributePointer(3, DataType.FLOAT, false)

    override val attributePointers: List<AttributePointer> = listOf(this.attributePointer)

    fun write(value: Vector3fc) {
        write(value.x(), value.y(), value.z())
    }

    fun write(x: Float, y: Float, z: Float) {
        this.dataWriter.writeFloat(x)
        this.dataWriter.writeFloat(y)
        this.dataWriter.writeFloat(z)
    }
}