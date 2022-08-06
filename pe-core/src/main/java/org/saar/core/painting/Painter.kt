package org.saar.core.painting

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbos.attachment.allocation.SimpleTextureAllocation
import org.saar.lwjgl.opengl.fbos.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D

interface Painter : RenderPass<RenderPassBuffers> {

    fun prepare() = Unit

    fun render()

    override fun prepare(context: RenderContext, buffers: RenderPassBuffers) = prepare()

    override fun render(context: RenderContext, buffers: RenderPassBuffers) = render()
}

fun Painter.renderToTexture(width: Int, height: Int, internalFormat: InternalFormat): MutableTexture2D {
    return MutableTexture2D.create().also { texture ->
        val fbo = Fbo.create(width, height).apply {
            val allocation = SimpleTextureAllocation(internalFormat)
            val buffer = TextureAttachmentBuffer(texture, allocation)
            val attachment = ColourAttachment(0, buffer)
            addAttachment(attachment)
            setDrawAttachments(attachment)
            setReadAttachment(attachment)
        }

        fbo.bindAsDraw()
        render()
        fbo.delete()
    }
}