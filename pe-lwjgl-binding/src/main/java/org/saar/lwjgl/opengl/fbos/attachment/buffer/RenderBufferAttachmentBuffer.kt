package org.saar.lwjgl.opengl.fbos.attachment.buffer

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer

class RenderBufferAttachmentBuffer(
    private val renderBuffer: RenderBuffer,
    private val internalFormat: InternalFormat,
) : AttachmentBuffer {

    override fun allocate(width: Int, height: Int) =
        this.renderBuffer.loadStorage(width, height, this.internalFormat)

    override fun allocateMultisampled(width: Int, height: Int, samples: Int) =
        this.renderBuffer.loadStorageMultisample(width, height, this.internalFormat, samples)

    override fun attachToFbo(index: AttachmentIndex) = this.renderBuffer.attachToFbo(index.value)

    override fun delete() = this.renderBuffer.delete()
}