package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer

class SimpleAllocationStrategy : AllocationStrategy {

    override fun allocate(buffer: AttachmentBuffer, width: Int, height: Int) =
        buffer.allocate(width, height)

}