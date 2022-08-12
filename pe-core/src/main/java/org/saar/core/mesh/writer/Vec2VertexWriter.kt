package org.saar.core.mesh.writer

import org.joml.Vector2fc
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.attribute.AttributeLinker
import org.saar.lwjgl.opengl.attribute.FloatAttributeLinker
import org.saar.lwjgl.util.DataWriter

class Vec2VertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    val attributeLinker: AttributeLinker = FloatAttributeLinker(2, DataType.FLOAT, false)

    override val attributeLinkers: List<AttributeLinker> = listOf(this.attributeLinker)

    fun write(value: Vector2fc) {
        write(value.x(), value.y())
    }

    fun write(x: Float, y: Float) {
        this.dataWriter.writeFloat(x)
        this.dataWriter.writeFloat(y)
    }
}