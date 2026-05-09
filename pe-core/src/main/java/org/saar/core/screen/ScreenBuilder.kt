package org.saar.core.screen

import org.saar.core.screen.image.ScreenImage
import org.saar.core.screen.image.SimpleScreenImage
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.Attachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.allocation.MultisampledAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.*

class ScreenBuilder(private val fbo: IFbo) {

    private val attachments = ArrayList<AttachmentEntry>()

    private var allocationStrategy: AllocationStrategy = SimpleAllocationStrategy()

    private var nextColourIndex = 0

    fun multisampled(samples: Int): ScreenBuilder {
        this.allocationStrategy = MultisampledAllocationStrategy(samples)
        return this
    }

    fun addColourTexture(format: InternalFormat, read: Boolean, draw: Boolean): ScreenBuilder {
        val index = ColourAttachmentIndex(this.nextColourIndex++)
        val buffer = TextureAttachmentBuffer(format)
        val attachment = Attachment(buffer, this.allocationStrategy)
        val image = SimpleScreenImage(attachment)
        this.attachments.add(AttachmentEntry(index, image, read, draw))
        return this
    }

    fun addColourRenderBuffer(format: InternalFormat, read: Boolean, draw: Boolean): ScreenBuilder {
        val index = ColourAttachmentIndex(this.nextColourIndex++)
        val buffer = RenderBufferAttachmentBuffer(format)
        val attachment = Attachment(buffer, this.allocationStrategy)
        val image = SimpleScreenImage(attachment)
        this.attachments.add(AttachmentEntry(index, image, read, draw))
        return this
    }

    fun addDepthTexture(format: InternalFormat): ScreenBuilder {
        val buffer = TextureAttachmentBuffer(format)
        val attachment = Attachment(buffer, this.allocationStrategy)
        val image = SimpleScreenImage(attachment)
        this.attachments.add(AttachmentEntry(DepthAttachmentIndex, image, read = false, draw = false))
        return this
    }

    fun addDepthRenderBuffer(format: InternalFormat): ScreenBuilder {
        val buffer = RenderBufferAttachmentBuffer(format)
        val attachment = Attachment(buffer, this.allocationStrategy)
        val image = SimpleScreenImage(attachment)
        this.attachments.add(AttachmentEntry(DepthAttachmentIndex, image, read = false, draw = false))
        return this
    }

    fun addStencilRenderBuffer(format: InternalFormat): ScreenBuilder {
        val buffer = RenderBufferAttachmentBuffer(format)
        val attachment = Attachment(buffer, this.allocationStrategy)
        val image = SimpleScreenImage(attachment)
        this.attachments.add(AttachmentEntry(StencilAttachmentIndex, image, read = false, draw = false))
        return this
    }

    fun addDepthStencilRenderBuffer(format: InternalFormat): ScreenBuilder {
        val buffer = RenderBufferAttachmentBuffer(format)
        val attachment = Attachment(buffer, this.allocationStrategy)
        val image = SimpleScreenImage(attachment)
        this.attachments.add(AttachmentEntry(DepthStencilAttachmentIndex, image, read = false, draw = false))
        return this
    }

    fun build(): OffScreen {
        val screen = SimpleScreen(this.fbo)

        attachments.forEach { screen.addScreenImage(it.index, it.image) }

        val readEntries = attachments.filter { it.read }
        require(readEntries.size <= 1) { "Multiple read attachments" }
        readEntries.firstOrNull()?.let { screen.setReadImages(it.index) }

        val drawIndices = attachments.filter { it.draw }.map { it.index }
        if (drawIndices.isNotEmpty()) {
            screen.setDrawImages(*drawIndices.toTypedArray())
        }

        this.fbo.ensureStatus()

        return screen
    }

    private data class AttachmentEntry(
        val index: AttachmentIndex,
        val image: ScreenImage,
        val read: Boolean,
        val draw: Boolean
    )
}
