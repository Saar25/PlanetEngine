package org.saar.core.painting

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.forward.ForwardRenderPass
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.textures.TextureTarget

interface Painter : RenderPass, PostProcessor, ForwardRenderPass, DeferredRenderPass {

    fun render()

    override fun render(context: RenderPassContext, buffers: PostProcessingBuffers) = render()

    override fun render(context: RenderPassContext, buffers: ForwardRenderingBuffers) = render()

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) = render()
}

fun Painter.renderToTexture(width: Int, height: Int,
                            colourFormatType: ColourFormatType = ColourFormatType.RGB8): Texture {
    return Texture.create(TextureTarget.TEXTURE_2D).also { texture ->
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