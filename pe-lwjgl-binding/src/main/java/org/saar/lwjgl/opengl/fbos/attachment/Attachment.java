package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.Fbo;
import org.saar.lwjgl.opengl.textures.Texture;

public interface Attachment {

    int getAttachmentPoint();

    AttachmentType getAttachmentType();

    Texture getTexture();

    void init(Fbo fbo);

    void delete();

}
