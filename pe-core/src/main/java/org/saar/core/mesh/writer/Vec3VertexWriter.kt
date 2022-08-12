package org.saar.core.mesh.writer

import org.joml.Vector3fc
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.AttributeLinker
import org.saar.lwjgl.opengl.attribute.FloatAttributeLinker
import org.saar.lwjgl.util.DataWriter

class Vec3VertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    val attributeLinker: AttributeLinker = FloatAttributeLinker(3, DataType.FLOAT, false)

    override val attributeLinkers: List<AttributeLinker> = listOf(this.attributeLinker)

    fun write(value: Vector3fc) {
        write(value.x(), value.y(), value.z())
    }

    fun write(x: Float, y: Float, z: Float) {
        this.dataWriter.writeFloat(x)
        this.dataWriter.writeFloat(y)
        this.dataWriter.writeFloat(z)
    }
}