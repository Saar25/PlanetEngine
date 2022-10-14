package org.saar.lwjgl.opengl.fbo.attachment.allocation

import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer

interface AllocationStrategy {

    fun allocate(buffer: AttachmentBuffer, width: Int, height: Int)

}