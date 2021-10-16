package org.saar.core.screen

import org.saar.lwjgl.opengl.fbos.FboBlitFilter
import org.saar.lwjgl.opengl.fbos.ScreenFbo
import org.saar.lwjgl.opengl.utils.GlBuffer

object MainScreen : Screen {

    private val fbo: ScreenFbo get() = ScreenFbo.getInstance()

    override fun getWidth() = this.fbo.width

    override fun getHeight() = this.fbo.height

    override fun copyTo(other: Screen, filter: FboBlitFilter, vararg buffers: GlBuffer) {
        setAsRead()
        other.setAsDraw()
        this.fbo.blitFramebuffer(
            0, 0, this.width, this.height,
            0, 0, other.width, other.height,
            filter, buffers)
    }

    override fun setAsDraw() = this.fbo.bindAsDraw()

    override fun setAsRead() = this.fbo.bindAsRead()
}