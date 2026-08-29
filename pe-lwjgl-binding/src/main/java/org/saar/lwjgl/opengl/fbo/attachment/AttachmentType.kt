package org.saar.lwjgl.opengl.fbo.attachment

import org.lwjgl.opengl.GL30

enum class AttachmentType(private val value: Int) {
    COLOR(GL30.GL_COLOR_ATTACHMENT0),
    DEPTH(GL30.GL_DEPTH_ATTACHMENT),
    STENCIL(GL30.GL_STENCIL_ATTACHMENT),
    DEPTH_STENCIL(GL30.GL_DEPTH_STENCIL_ATTACHMENT),
    ;

    fun get() = this.value
}
