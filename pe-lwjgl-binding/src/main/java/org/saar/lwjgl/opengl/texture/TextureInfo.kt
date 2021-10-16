package org.saar.lwjgl.opengl.texture

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import java.nio.ByteBuffer

data class TextureInfo(
    val width: Int,
    val height: Int,
    val formatType: FormatType,
    val dataType: DataType,
    val data: ByteBuffer
)