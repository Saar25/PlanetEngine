package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.fbos.attachment.Attachment;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;

public interface ModifiableFbo extends ReadOnlyFbo {

    /**
     * Adds an attachment to the fbo
     *
     * @param attachment the attachment to add
     */
    void addAttachment(Attachment attachment);

    /**
     * Sets the read attachments of the fbo
     *
     * @param attachment the read attachment
     */
    void setReadAttachment(ColourAttachment attachment);

    /**
     * Sets the draw attachments of the fbo
     *
     * @param attachments the draw attachments
     */
    void setDrawAttachments(Attachment... attachments);

}
