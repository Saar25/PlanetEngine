package org.saar.lwjgl.opengl.fbo.attachment.index

import org.lwjgl.opengl.GL30
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType

class ColorAttachmentIndex private constructor(val index: Int) : AttachmentIndex {

    override val value = AttachmentType.COLOUR.get() + index

    override val type = AttachmentType.COLOUR

    companion object {
        private val maxColourAttachments by lazy {
            GL30.glGetInteger(GL30.GL_MAX_COLOR_ATTACHMENTS)
        }

        private val cache by lazy {
            Array(maxColourAttachments) { ColorAttachmentIndex(it) }
        }

        @JvmStatic
        fun at(index: Int): ColorAttachmentIndex {
            require(index in 0 until maxColourAttachments) {
                "Color attachment index $index out of range (max $maxColourAttachments)"
            }
            return cache[index]
        }
    }
}