package org.saar.lwjgl.opengl.fbo.attachment

import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex

interface IAttachment {

    fun attach(fbo: Int, index: AttachmentIndex)

    fun allocate(width: Int, height: Int)

    fun delete()

}
