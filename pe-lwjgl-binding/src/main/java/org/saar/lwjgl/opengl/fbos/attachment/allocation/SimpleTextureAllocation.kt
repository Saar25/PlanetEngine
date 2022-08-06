package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class SimpleTextureAllocation(private val internalFormat: InternalFormat) : TextureAllocationStrategy {

    override fun allocate(texture: MutableTexture2D, width: Int, height: Int) {
        texture.allocate(0, this.internalFormat, width, height)
    }
}