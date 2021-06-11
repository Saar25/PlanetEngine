package org.saar.core.mesh.writer

import org.joml.Matrix4fc
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.AttributeLinker
import org.saar.lwjgl.opengl.objects.attributes.FloatAttributeLinker
import org.saar.lwjgl.util.DataWriter

class Mat4VertexWriter(private val dataWriter: DataWriter) : VertexDataWriter {

    override val attributeLinkers: List<AttributeLinker> = listOf(
        FloatAttributeLinker(4, DataType.FLOAT, false),
        FloatAttributeLinker(4, DataType.FLOAT, false),
        FloatAttributeLinker(4, DataType.FLOAT, false),
        FloatAttributeLinker(4, DataType.FLOAT, false)
    )

    fun write(value: Matrix4fc) = this.dataWriter.write4x4f(value)
}