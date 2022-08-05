package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class MultisampledTextureAllocation @JvmOverloads constructor(
    private val internalFormat: InternalFormat,
    private val samples: Int,
    private val fixedSampleLocations: Boolean = false,
) : TextureAllocationStrategy {

    override fun allocate(texture: MutableTexture2D, width: Int, height: Int) {
        texture.allocateMultisample(this.samples, this.internalFormat, width, height, this.fixedSampleLocations)
    }
}