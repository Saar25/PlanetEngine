package org.saar.lwjgl.opengl.fbo;

import org.saar.lwjgl.opengl.fbo.attachment.IAttachment;
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTarget;
import org.saar.lwjgl.opengl.fbo.rendertarget.ReadRenderTarget;

public interface ModifiableFbo extends ReadOnlyFbo {

    /**
     * Adds an attachment to the fbo
     *
     * @param index      the index of the attachment
     * @param attachment the attachment to add
     */
    void addAttachment(AttachmentIndex index, IAttachment attachment);

    /**
     * Sets the draw target of the fbo
     *
     * @param target the draw target
     */
    void setDrawTarget(DrawRenderTarget target);

    /**
     * Sets the read source of the fbo
     *
     * @param target the read source
     */
    void setReadTarget(ReadRenderTarget target);

}
