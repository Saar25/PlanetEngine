package org.saar.core.painting

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.fbo.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget
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
            val allocation = SimpleAllocationStrategy()
            val buffer = TextureAttachmentBuffer(texture, internalFormat)
            val attachment = ColourAttachment(0, buffer, allocation)
            val renderTarget = IndexRenderTarget(attachment.index)
            val index = ColourAttachmentIndex(0)

            addAttachment(index, attachment)
            setDrawTarget(renderTarget)
            setReadTarget(renderTarget)
        }

        fbo.bindAsDraw()
        render()
        fbo.delete()
    }
}