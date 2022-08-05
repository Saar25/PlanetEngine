package org.saar.lwjgl.opengl.fbos.attachment.buffer

import org.saar.lwjgl.opengl.fbos.attachment.AttachmentIndex
import org.saar.lwjgl.opengl.fbos.attachment.allocation.TextureAllocationStrategy
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.parameter.TextureAnisotropicFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue

class TextureAttachmentBuffer @JvmOverloads constructor(
    private val attachmentIndex: AttachmentIndex,
    private val texture: MutableTexture2D,
    private val allocation: TextureAllocationStrategy,
    private val level: Int = 0,
) : AttachmentBuffer {

    companion object {
        private val defaultParameters = arrayOf(
            TextureAnisotropicFilterParameter(4f),
            TextureMagFilterParameter(MagFilterValue.LINEAR),
            TextureMinFilterParameter(MinFilterValue.NEAREST))
    }


    init {
        this.texture.applyParameters(defaultParameters)
        if (this.texture.width > 0 && this.texture.height > 0) {
            this.texture.generateMipmap()
        }
    }

    override fun allocate(width: Int, height: Int) = this.allocation.allocate(this.texture)

    override fun allocateMultisample(width: Int, height: Int, samples: Int) = this.allocation.allocate(this.texture)

    override fun attachToFbo(attachment: Int) = this.texture.attachToFbo(this.attachmentIndex.get(), this.level)

    override fun delete() = this.texture.delete()
}