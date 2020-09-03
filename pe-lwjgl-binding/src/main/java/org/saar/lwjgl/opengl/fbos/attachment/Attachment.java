package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.textures.Texture;

public interface Attachment {

    int getAttachmentPoint();

    AttachmentType getAttachmentType();

    Texture getTexture();

    void init(ReadOnlyFbo fbo);

    void delete();

}
