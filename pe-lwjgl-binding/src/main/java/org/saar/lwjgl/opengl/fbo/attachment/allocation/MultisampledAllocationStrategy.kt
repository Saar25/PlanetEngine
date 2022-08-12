package org.saar.lwjgl.opengl.fbo.attachment.allocation

import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer

class MultisampledAllocationStrategy(private val samples: Int) : AllocationStrategy {

    override fun allocate(buffer: AttachmentBuffer, width: Int, height: Int) =
        buffer.allocateMultisampled(width, height, this.samples)
}