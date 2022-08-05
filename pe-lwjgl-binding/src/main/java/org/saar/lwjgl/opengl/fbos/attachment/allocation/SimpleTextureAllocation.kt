package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class SimpleTextureAllocation(
    private val width: Int,
    private val height: Int,
    private val internalFormat: InternalFormat,
    private val format: FormatType,
    private val dataType: DataType,
    private val level: Int = 0,
    private val border: Int = 0,
) : TextureAllocationStrategy {

    override fun allocate(texture: MutableTexture2D) {
        texture.allocate(this.level, this.internalFormat, this.width,
            this.height, this.border, this.format, this.dataType, null)
    }
}