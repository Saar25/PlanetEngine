package org.saar.lwjgl.opengl.fbos.attachment.buffer

import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex

interface AttachmentBuffer {
    fun allocate(width: Int, height: Int)

    fun attachToFbo(index: AttachmentIndex)

    fun delete()
}