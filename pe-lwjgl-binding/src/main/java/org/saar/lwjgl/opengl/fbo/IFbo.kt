package org.saar.lwjgl.opengl.fbo

import org.saar.lwjgl.opengl.fbo.attachment.IAttachment
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTarget
import org.saar.lwjgl.opengl.fbo.rendertarget.ReadRenderTarget

interface IFbo : ReadOnlyFbo {
    /**
     * Adds an attachment to the fbo
     * 
     * @param index      the index of the attachment
     * @param attachment the attachment to add
     */
    fun addAttachment(index: AttachmentIndex, attachment: IAttachment)

    /**
     * Sets the draw target of the fbo
     * 
     * @param target the draw target
     */
    fun setDrawTarget(target: DrawRenderTarget)

    /**
     * Sets the read source of the fbo
     * 
     * @param target the read source
     */
    fun setReadTarget(target: ReadRenderTarget)

    /**
     * Delete the fbo
     */
    fun delete()
}
