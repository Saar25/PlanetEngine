package org.saar.lwjgl.opengl.fbo.rendertarget

import org.lwjgl.opengl.GL20

class DrawRenderTargetComposite private constructor(private val buffers: IntArray) : DrawRenderTarget {

    constructor(vararg drawBuffers: SingleRenderTarget) : this(drawBuffers.map { it.index.value }.toIntArray())

    constructor(drawBuffers: Iterable<SingleRenderTarget>) : this(drawBuffers.map { it.index.value }.toIntArray())

    override fun setAsDraw() = GL20.glDrawBuffers(this.buffers)
}