package org.saar.core.painting

import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.texture.MutableTexture2D

interface Painter : RenderPass<RenderPassBuffers> {

    fun prepare() = Unit

    fun render()

    override fun prepare(context: RenderPassContext, buffers: RenderPassBuffers) = prepare()

    override fun render(context: RenderPassContext, buffers: RenderPassBuffers) = render()
}

fun Painter.renderToTexture(width: Int, height: Int,
                            colourFormatType: ColourFormatType = ColourFormatType.RGB8): MutableTexture2D {
    return MutableTexture2D.create().also { texture ->
        val fbo = Fbo.create(width, height).apply {
            val attachment = ColourAttachment.withTexture(0,
                texture, colourFormatType)
            addAttachment(attachment)
            setDrawAttachments(attachment)
            setReadAttachment(attachment)
        }

        fbo.bindAsDraw()
        render()
        fbo.delete()
    }
}