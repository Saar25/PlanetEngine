package org.saar.lwjgl.opengl.fbo.attachment.buffer

import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex

interface AttachmentBuffer {

    fun allocate(width: Int, height: Int)

    fun allocateMultisampled(width: Int, height: Int, samples: Int)

    fun attachToFbo(fbo: Int, index: AttachmentIndex)

    fun delete()

}