package org.saar.core.screen

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.buffer.RenderBufferAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.renderbuffer.RenderBuffer
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun buildScreen(width: Int, height: Int, block: ScreenDSL.() -> Unit): OffScreen {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return buildScreen(Fbo.create(), width, height, block)
}

@OptIn(ExperimentalContracts::class)
inline fun buildScreen(fbo: IFbo, width: Int, height: Int, block: ScreenDSL.() -> Unit): OffScreen {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return ScreenDSL(fbo).apply(block).build(width, height)
}

class ScreenDSL(fbo: IFbo) {

    private val screenBuilder = ScreenBuilder(fbo)

    fun multisampled(samples: Int) {
        this.screenBuilder.multisampled(samples)
    }

    fun colorAttachment(texture: MutableTexture2D, format: InternalFormat) {
        this.screenBuilder.addColorAttachment(TextureAttachmentBuffer(texture, format))
    }

    fun depthAttachment(texture: MutableTexture2D, format: InternalFormat) {
        this.screenBuilder.addDepthAttachment(TextureAttachmentBuffer(texture, format))
    }

    fun stencilAttachment(texture: MutableTexture2D, format: InternalFormat) {
        this.screenBuilder.addStencilAttachment(TextureAttachmentBuffer(texture, format))
    }

    fun depthStencilAttachment(texture: MutableTexture2D, format: InternalFormat) {
        this.screenBuilder.addDepthStencilAttachment(TextureAttachmentBuffer(texture, format))
    }

    fun colorAttachment(renderBuffer: RenderBuffer, format: InternalFormat) {
        this.screenBuilder.addColorAttachment(RenderBufferAttachmentBuffer(renderBuffer, format))
    }

    fun depthAttachment(renderBuffer: RenderBuffer, format: InternalFormat) {
        this.screenBuilder.addDepthAttachment(RenderBufferAttachmentBuffer(renderBuffer, format))
    }

    fun stencilAttachment(renderBuffer: RenderBuffer, format: InternalFormat) {
        this.screenBuilder.addStencilAttachment(RenderBufferAttachmentBuffer(renderBuffer, format))
    }

    fun depthStencilAttachment(renderBuffer: RenderBuffer, format: InternalFormat) {
        this.screenBuilder.addDepthStencilAttachment(RenderBufferAttachmentBuffer(renderBuffer, format))
    }

    fun build(width: Int, height: Int) = this.screenBuilder.build(width, height)
}
