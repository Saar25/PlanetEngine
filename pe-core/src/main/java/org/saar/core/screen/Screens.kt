package org.saar.core.screen

import org.saar.core.screen.image.SimpleScreenImage
import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.Attachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.DepthAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.DepthStencilAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.StencilAttachmentIndex
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTargetComposite
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget

object Screens {

    @JvmStatic
    fun fromPrototype(prototype: ScreenPrototype, fbo: IFbo, allocation: AllocationStrategy): OffScreen {
        val screenImagesMap = buildMap {
            prototype.colorBuffers.withIndex().forEach { (index, buffer) -> put(ColorAttachmentIndex.at(index), buffer) }
            prototype.depthBuffer?.let { put(DepthAttachmentIndex, it) }
            prototype.stencilBuffer?.let { put(StencilAttachmentIndex, it) }
            prototype.depthStencilBuffer?.let { put(DepthStencilAttachmentIndex, it) }
        }

        val attachments = screenImagesMap.mapValues { (index, buffer) ->
            val attachment = Attachment(buffer, allocation)
            fbo.addAttachment(index, attachment)
            SimpleScreenImage(attachment)
        }

        setReadTarget(fbo, prototype)
        setDrawTargets(fbo, prototype.colorBuffers)

        return ScreenPrototypeWrapper(fbo, attachments)
    }

    private fun setReadTarget(fbo: IFbo, prototype: ScreenPrototype) {
        prototype.readIndex?.let {
            val target = IndexRenderTarget(it)
            fbo.setReadTarget(target)
        }
    }

    private fun setDrawTargets(fbo: IFbo, prototypes: Collection<AttachmentBuffer>) {
        val drawRenderTargets = List(prototypes.size) { ColorAttachmentIndex.at(it) }.map(::IndexRenderTarget)

        val renderTarget = DrawRenderTargetComposite(drawRenderTargets)

        fbo.setDrawTarget(renderTarget)
    }
}