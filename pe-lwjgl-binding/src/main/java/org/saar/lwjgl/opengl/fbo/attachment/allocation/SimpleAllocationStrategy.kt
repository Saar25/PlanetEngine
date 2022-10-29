package org.saar.lwjgl.opengl.fbo.attachment.allocation

import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer

class SimpleAllocationStrategy : AllocationStrategy {

    override fun allocate(buffer: AttachmentBuffer, width: Int, height: Int) =
        buffer.allocate(width, height)

}