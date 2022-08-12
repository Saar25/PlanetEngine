package org.saar.core.mesh.writer

import org.joml.Vector4fc
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.AttributeLinker
import org.saar.lwjgl.opengl.attribute.FloatAttributeLinker
import org.saar.lwjgl.util.DataWriter

class Vec4VertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    val attributeLinker: AttributeLinker = FloatAttributeLinker(4, DataType.FLOAT, false)

    override val attributeLinkers: List<AttributeLinker> = listOf(this.attributeLinker)

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