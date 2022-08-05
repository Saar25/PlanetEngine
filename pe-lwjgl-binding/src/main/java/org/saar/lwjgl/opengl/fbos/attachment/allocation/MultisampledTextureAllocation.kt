package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class MultisampledTextureAllocation(
    private val width: Int,
    private val height: Int,
    private val internalFormat: InternalFormat,
    private val samples: Int,
    private val fixedSampleLocations: Boolean = false,
) : TextureAllocationStrategy {

    override fun allocate(texture: MutableTexture2D) {
        texture.allocateMultisample(this.samples, this.internalFormat,
            this.width, this.height, this.fixedSampleLocations)
    }
}