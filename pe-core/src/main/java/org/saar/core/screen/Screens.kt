package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.Attachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.DepthAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.DepthStencilAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.StencilAttachmentIndex

object Screens {

    @JvmStatic
    fun fromPrototype(
        prototype: ScreenPrototype,
        fbo: IFbo,
        allocation: AllocationStrategy,
        width: Int,
        height: Int
    ): OffScreen {
        val screen = MutableScreen(fbo, width, height)

        prototype.colorBuffers.withIndex().forEach { (i, buffer) ->
            val index = ColorAttachmentIndex.at(i)
            val attachment = Attachment(buffer, allocation)
            screen.addAttachment(index, attachment)
        }
        prototype.depthBuffer
            ?.let { Attachment(it, allocation) }
            ?.let { screen.addAttachment(DepthAttachmentIndex, it) }
        prototype.stencilBuffer
            ?.let { Attachment(it, allocation) }
            ?.let { screen.addAttachment(StencilAttachmentIndex, it) }
        prototype.depthStencilBuffer
            ?.let { Attachment(it, allocation) }
            ?.let { screen.addAttachment(DepthStencilAttachmentIndex, it) }

        prototype.readIndex?.let { screen.setReadImage(it) }
        val drawRenderTargets = List(prototype.colorBuffers.size) { ColorAttachmentIndex.at(it) }
        screen.setDrawImages(drawRenderTargets)

        return screen
    }

    @JvmOverloads
    fun ScreenPrototype.toScreen(
        fbo: IFbo,
        allocation: AllocationStrategy = SimpleAllocationStrategy,
        width: Int = 0,
        height: Int = 0
    ): OffScreen {
        return fromPrototype(this, fbo, allocation, width, height)
    }

    @JvmStatic
    fun ScreenPrototype.toScreen(
        fbo: IFbo,
        width: Int = 0,
        height: Int = 0
    ): OffScreen = this.toScreen(fbo, SimpleAllocationStrategy, width, height)
}