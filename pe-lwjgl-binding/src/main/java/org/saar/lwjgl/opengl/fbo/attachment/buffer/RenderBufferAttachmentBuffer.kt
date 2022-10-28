package org.saar.lwjgl.opengl.fbo.attachment.buffer

import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.renderbuffer.RenderBuffer

class RenderBufferAttachmentBuffer(
    private val renderBuffer: RenderBuffer,
    private val internalFormat: InternalFormat,
) : AttachmentBuffer {

    constructor(internalFormat: InternalFormat) : this(RenderBuffer.create(), internalFormat)

    override fun allocate(width: Int, height: Int) =
        this.renderBuffer.loadStorage(width, height, this.internalFormat)

    override fun allocateMultisampled(width: Int, height: Int, samples: Int) =
        this.renderBuffer.loadStorageMultisample(width, height, this.internalFormat, samples)

    override fun attachToFbo(index: AttachmentIndex) = this.renderBuffer.attachToFbo(index.value)

    override fun delete() = this.renderBuffer.delete()
}