package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer

class MultisampledAllocationStrategy(private val samples: Int) : AllocationStrategy {

    override fun allocate(buffer: AttachmentBuffer, width: Int, height: Int) =
        buffer.allocateMultisampled(width, height, this.samples)
}