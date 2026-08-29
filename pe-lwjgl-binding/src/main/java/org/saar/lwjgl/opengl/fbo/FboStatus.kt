package org.saar.lwjgl.opengl.fbo

import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL32
import org.saar.lwjgl.opengl.fbo.exceptions.*

object FboStatus {

    private const val MESSAGE = "Framebuffer creation failed"

    @JvmStatic
    @Throws(FrameBufferException::class)
    fun ensureStatus(status: Int) {
        when (status) {
            GL30.GL_FRAMEBUFFER_COMPLETE -> {}
            GL30.GL_FRAMEBUFFER_UNDEFINED ->
                throw FboUndefinedException(MESSAGE)

            GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT ->
                throw FboIncompleteAttachmentException(MESSAGE)

            GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT ->
                throw FboIncompleteMissingAttachmentException(MESSAGE)

            GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER ->
                throw FboIncompleteDrawBufferException(MESSAGE)

            GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER ->
                throw FboIncompleteReadBufferException(MESSAGE)

            GL30.GL_FRAMEBUFFER_UNSUPPORTED ->
                throw FboUnsupportedException(MESSAGE)

            GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE ->
                throw FboIncompleteMultisampleException(MESSAGE)

            GL32.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS ->
                throw FboIncompleteLayerTargetsException(MESSAGE)
        }
    }
}