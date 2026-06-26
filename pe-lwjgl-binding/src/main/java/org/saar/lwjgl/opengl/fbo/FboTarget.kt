package org.saar.lwjgl.opengl.fbo

import org.lwjgl.opengl.GL30

enum class FboTarget(private val value: Int) {
    FRAMEBUFFER(GL30.GL_FRAMEBUFFER),
    DRAW_FRAMEBUFFER(GL30.GL_DRAW_FRAMEBUFFER),
    READ_FRAMEBUFFER(GL30.GL_READ_FRAMEBUFFER),
    ;

    fun get() = this.value
}
