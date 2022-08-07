package org.saar.lwjgl.opengl.fbos.attachment.buffer

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.parameter.TextureAnisotropicFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue

class TextureAttachmentBuffer(
    private val texture: MutableTexture2D,
    private val internalFormat: InternalFormat,
) : AttachmentBuffer {

    companion object {
        private val defaultParameters = arrayOf(
            TextureAnisotropicFilterParameter(4f),
            TextureMagFilterParameter(MagFilterValue.LINEAR),
            TextureMinFilterParameter(MinFilterValue.NEAREST))
    }


    private fun configureTexture() {
        this.texture.applyParameters(defaultParameters)
        if (this.texture.width > 0 && this.texture.height > 0) {
            this.texture.generateMipmap()
        }
    }

    override fun allocate(width: Int, height: Int) =
        this.texture.allocate(0, this.internalFormat, width, height).also { configureTexture() }

    override fun allocateMultisampled(width: Int, height: Int, samples: Int) =
        this.texture.allocateMultisample(samples, this.internalFormat, width, height, true).also { configureTexture() }

    override fun attachToFbo(index: AttachmentIndex) = this.texture.attachToFbo(index.value, 0)

    override fun delete() = this.texture.delete()
}