package org.saar.lwjgl.opengl.fbo.attachment.index

import org.lwjgl.opengl.GL30
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType

class ColorAttachmentIndex private constructor(val index: Int) : AttachmentIndex {

    override val value = AttachmentType.COLOR.get() + index

    override val type = AttachmentType.COLOR

    companion object {
        private val maxColorAttachments by lazy {
            GL30.glGetInteger(GL30.GL_MAX_COLOR_ATTACHMENTS)
        }

        private val cache by lazy {
            Array(maxColorAttachments) { ColorAttachmentIndex(it) }
        }

        @JvmStatic
        fun at(index: Int): ColorAttachmentIndex {
            require(index in 0 until maxColorAttachments) {
                "Color attachment index $index out of range (max $maxColorAttachments)"
            }
            return cache[index]
        }
    }
}