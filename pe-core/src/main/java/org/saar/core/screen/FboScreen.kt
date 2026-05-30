package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.FboBlitFilter
import org.saar.lwjgl.opengl.fbo.ReadOnlyFbo
import org.saar.lwjgl.opengl.utils.GlBuffer

abstract class FboScreen : Screen {

    override val width get() = this.fbo.width

    override val height get() = this.fbo.height

    override fun copyTo(other: Screen, filter: FboBlitFilter, vararg buffers: GlBuffer) {
        other.setAsDraw()
        this.fbo.blitFramebuffer(
            0, 0, this.width, this.height,
            0, 0, other.width, other.height,
            filter, buffers)
    }

    override fun setAsDraw() = this.fbo.bindAsDraw()

    protected abstract val fbo: ReadOnlyFbo
}
