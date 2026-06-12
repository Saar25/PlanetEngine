package org.saar.lwjgl.opengl.fbo.attachment.allocation

import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer

object SimpleAllocationStrategy : AllocationStrategy {

    override fun allocate(buffer: AttachmentBuffer, width: Int, height: Int) =
        buffer.allocate(width, height)

}