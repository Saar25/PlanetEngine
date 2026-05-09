package org.saar.core.screen

import org.saar.core.screen.image.SimpleScreenImage
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

    private val layers = ArrayList<Layer>()

    private var allocationStrategy: AllocationStrategy = SimpleAllocationStrategy()

    private var nextColourIndex = 0

    fun multisampled(samples: Int): ScreenBuilder {
        this.allocationStrategy = MultisampledAllocationStrategy(samples)
        return this
    }

    @JvmOverloads
    fun addColourTexture(texture: MutableTexture2D = MutableTexture2D.create(),
                         format: InternalFormat,
                         read: Boolean,
                         draw: Boolean): ScreenBuilder {
        val index = ColourAttachmentIndex(this.nextColourIndex++)
        this.layers.add(Layer(index, TextureAttachmentBuffer(texture, format), read, draw))
        return this
    }

    fun addColourRenderBuffer(format: InternalFormat, read: Boolean, draw: Boolean): ScreenBuilder {
        val index = ColourAttachmentIndex(this.nextColourIndex++)
        this.layers.add(Layer(index, RenderBufferAttachmentBuffer(format), read, draw))
        return this
    }

    @JvmOverloads
    fun addDepthTexture(texture: MutableTexture2D = MutableTexture2D.create(), format: InternalFormat): ScreenBuilder {
        this.layers.add(Layer(DepthAttachmentIndex, TextureAttachmentBuffer(texture, format),
            read = false, draw = false))
        return this
    }

    fun addDepthRenderBuffer(format: InternalFormat): ScreenBuilder {
        this.layers.add(Layer(DepthAttachmentIndex, RenderBufferAttachmentBuffer(format),
            read = false, draw = false))
        return this
    }

    fun addStencilRenderBuffer(format: InternalFormat): ScreenBuilder {
        this.layers.add(Layer(StencilAttachmentIndex, RenderBufferAttachmentBuffer(format),
            read = false, draw = false))
        return this
    }

    fun addDepthStencilRenderBuffer(format: InternalFormat): ScreenBuilder {
        this.layers.add(Layer(DepthStencilAttachmentIndex, RenderBufferAttachmentBuffer(format),
            read = false, draw = false))
        return this
    }

    fun build(): OffScreen {
        val screen = SimpleScreen(this.fbo)

        layers.forEach { layer ->
            val image = SimpleScreenImage(Attachment(layer.buffer, this.allocationStrategy))
            screen.addScreenImage(layer.index, image)
        }

        val readEntries = layers.filter { it.read }
        require(readEntries.size <= 1) { "Multiple read attachments" }
        readEntries.firstOrNull()?.let { screen.setReadImages(it.index) }

        val drawIndices = layers.filter { it.draw }.map { it.index }
        if (drawIndices.isNotEmpty()) {
            screen.setDrawImages(*drawIndices.toTypedArray())
        }

        this.fbo.ensureStatus()

        return screen
    }

    private class Layer(
        val index: AttachmentIndex,
        val buffer: AttachmentBuffer,
        val read: Boolean,
        val draw: Boolean,
    )
}
