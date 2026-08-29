package org.saar.lwjgl.opengl.fbo

import org.lwjgl.opengl.GL11

enum class FboBlitFilter(private val value: Int) {
    NEAREST(GL11.GL_NEAREST),
    LINEAR(GL11.GL_LINEAR),
    ;

    fun get() = this.value
}
