package org.saar.core.screen

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.Attachment
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.allocation.MultisampledAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.*
import org.saar.lwjgl.opengl.texture.MutableTexture2D

class ScreenBuilder(private val fbo: IFbo) {

    private val layers = mutableListOf<Layer>()

    private var allocationStrategy: AllocationStrategy = SimpleAllocationStrategy()

    private var nextColourIndex = 0

    fun multisampled(samples: Int): ScreenBuilder {
        this.allocationStrategy = MultisampledAllocationStrategy(samples)
        return this
    }

    @JvmOverloads
    fun addColourTexture(texture: MutableTexture2D = MutableTexture2D.create(), format: InternalFormat): ScreenBuilder {
        return addColorAttachment(TextureAttachmentBuffer(texture, format))
    }

    fun addColorRenderBuffer(format: InternalFormat): ScreenBuilder {
        return addColorAttachment(RenderBufferAttachmentBuffer(format))
    }

    fun addColorAttachment(buffer: AttachmentBuffer): ScreenBuilder {
        val index = ColorAttachmentIndex.at(this.nextColourIndex++)
        this.layers.add(Layer(index, buffer, read = false, draw = true))
        return this
    }

    fun addDepthAttachment(buffer: AttachmentBuffer): ScreenBuilder {
        val layer = Layer(DepthAttachmentIndex, buffer, read = false, draw = false)
        this.layers.add(layer)
        return this
    }

    fun addStencilRenderBuffer(format: InternalFormat) = addStencilAttachment(RenderBufferAttachmentBuffer(format))

    fun addStencilAttachment(buffer: AttachmentBuffer): ScreenBuilder {
        val layer = Layer(StencilAttachmentIndex, buffer, read = false, draw = false)
        this.layers.add(layer)
        return this
    }

    fun addDepthStencilRenderBuffer(format: InternalFormat) =
        addDepthStencilAttachment(RenderBufferAttachmentBuffer(format))

    fun addDepthStencilAttachment(buffer: AttachmentBuffer): ScreenBuilder {
        val layer = Layer(DepthStencilAttachmentIndex, buffer, read = false, draw = false)
        this.layers.add(layer)
        return this
    }

    @JvmOverloads
    fun setRead(read: Boolean = true): ScreenBuilder {
        val layer = this.layers.lastOrNull()
            ?: throw IllegalStateException("Cannot set read before adding a color attachment")
        require(layer.index is ColorAttachmentIndex) { "Cannot set read on a non-color attachment" }
        layer.read = read
        return this
    }

    @JvmOverloads
    fun setDraw(draw: Boolean = true): ScreenBuilder {
        val layer = this.layers.lastOrNull()
            ?: throw IllegalStateException("Cannot set draw before adding a color attachment")
        require(layer.index is ColorAttachmentIndex) { "Cannot set draw on a non-color attachment" }
        layer.draw = draw
        return this
    }

    fun build(): OffScreen {
        val screen = MutableScreen(this.fbo)

        this.layers.forEach { layer ->
            val attachment = Attachment(layer.buffer, this.allocationStrategy)
            screen.addAttachment(layer.index, attachment)
        }

        val readEntries = this.layers.filter { it.read }
        require(readEntries.size <= 1) { "Multiple read attachments" }
        readEntries.firstOrNull()?.let { screen.setReadImage(it.index as ColorAttachmentIndex) }

        val drawIndices = this.layers.filter { it.draw }.map { it.index as ColorAttachmentIndex }
        if (drawIndices.isNotEmpty()) {
            screen.setDrawImages(drawIndices)
        }

        return screen
    }

    private class Layer(
        val index: AttachmentIndex,
        val buffer: AttachmentBuffer,
        var read: Boolean,
        var draw: Boolean,
    )
}
