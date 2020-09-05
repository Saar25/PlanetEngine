package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.fbos.attachment.Attachment;

public interface ModifiableFbo extends ReadOnlyFbo {

    void addAttachment(Attachment attachment);

    void setReadAttachment(Attachment attachment);

    void setDrawAttachments(Attachment... attachments);

}
