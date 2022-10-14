package org.saar.lwjgl.opengl.fbo.copy

import org.saar.lwjgl.opengl.fbo.DrawableFbo
import org.saar.lwjgl.opengl.fbo.FboBlitFilter
import org.saar.lwjgl.opengl.fbo.ReadableFbo
import org.saar.lwjgl.opengl.utils.GlBuffer

class BlitCopyStrategy(
    private val blitFilter: FboBlitFilter,
    private val buffers: Array<GlBuffer>,
) : CopyStrategy {

    override fun copy(from: ReadableFbo, to: DrawableFbo) {
        to.bindAsDraw()
        from.blitFramebuffer(
            0, 0, from.width, from.height,
            0, 0, to.width, to.height,
            this.blitFilter, this.buffers)
    }
}