package org.saar.lwjgl.opengl.fbos.attachment.buffer

import org.saar.lwjgl.opengl.fbos.attachment.AttachmentIndex
import org.saar.lwjgl.opengl.fbos.attachment.allocation.RenderBufferAllocationStrategy
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer

class RenderBufferAttachmentBuffer(
    private val attachmentIndex: AttachmentIndex,
    private val renderBuffer: RenderBuffer,
    private val allocation: RenderBufferAllocationStrategy,
) : AttachmentBuffer {

    override fun allocate(width: Int, height: Int) = this.allocation.allocate(this.renderBuffer)

    override fun allocateMultisample(width: Int, height: Int, samples: Int) =
        this.allocation.allocate(this.renderBuffer)

    override fun attachToFbo(attachment: Int) = this.renderBuffer.attachToFbo(this.attachmentIndex.get())

    override fun delete() = this.renderBuffer.delete()
}