package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer

interface AllocationStrategy {

    fun allocate(buffer: AttachmentBuffer, width: Int, height: Int)

}