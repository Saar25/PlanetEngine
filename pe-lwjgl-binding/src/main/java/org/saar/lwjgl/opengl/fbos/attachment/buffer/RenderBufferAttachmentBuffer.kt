package org.saar.lwjgl.opengl.fbos.attachment.buffer

import org.saar.lwjgl.opengl.fbos.attachment.AttachmentIndex
import org.saar.lwjgl.opengl.fbos.attachment.allocation.RenderBufferAllocationStrategy
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer

class RenderBufferAttachmentBuffer(
    private val renderBuffer: RenderBuffer,
    private val allocation: RenderBufferAllocationStrategy,
) : AttachmentBuffer {

    override fun allocate() = this.allocation.allocate(this.renderBuffer)

    override fun attachToFbo(index: AttachmentIndex) = this.renderBuffer.attachToFbo(index.get())

    override fun delete() = this.renderBuffer.delete()
}