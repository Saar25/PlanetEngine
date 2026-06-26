package org.saar.lwjgl.opengl.fbo.attachment

import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.AttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex

class Attachment(
    private val buffer: AttachmentBuffer,
    private val allocation: AllocationStrategy
) : IAttachment {

    override fun attach(fbo: Int, index: AttachmentIndex) = this.buffer.attachToFbo(fbo, index)

    override fun allocate(width: Int, height: Int) = this.allocation.allocate(this.buffer, width, height)

    override fun delete() = this.buffer.delete()
}
