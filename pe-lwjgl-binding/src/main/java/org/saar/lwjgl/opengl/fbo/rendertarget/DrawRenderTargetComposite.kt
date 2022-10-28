package org.saar.lwjgl.opengl.fbo.rendertarget

import org.lwjgl.opengl.GL20

class DrawRenderTargetComposite(vararg drawBuffers: SingleRenderTarget) : DrawRenderTarget {

    private val buffers = drawBuffers.map { it.index.value }.toIntArray()

    override fun setAsDraw() = GL20.glDrawBuffers(this.buffers)
}