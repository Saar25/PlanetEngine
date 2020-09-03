package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;

public class ColourAttachment implements IColourAttachment, Attachment {

    private final int index;
    private final AttachmentBuffer buffer;

    public ColourAttachment(int index, AttachmentBuffer buffer) {
        this.index = index;
        this.buffer = buffer;
    }

    public static ColourAttachment withTexture(int index, Texture texture) {
        final AttachmentBuffer buffer = new AttachmentTextureBuffer(texture);
        return new ColourAttachment(index, buffer);
    }

    public static ColourAttachment withTexture(int index) {
        final Texture texture = Texture.create(TextureTarget.TEXTURE_2D);
        return ColourAttachment.withTexture(index, texture);
    }

    public static ColourAttachment withRenderBuffer(int index, RenderBuffer texture) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(texture);
        return new ColourAttachment(index, buffer);
    }

    public static ColourAttachment withRenderBuffer(int index) {
        final RenderBuffer texture = RenderBuffer.create();
        return ColourAttachment.withRenderBuffer(index, texture);
    }

    private AttachmentBuffer getBuffer() {
        return this.buffer;
    }

    @Override
    public int getAttachmentPoint() {
        return AttachmentType.COLOUR.get() + this.index;
    }

    @Override
    public void init(ReadOnlyFbo fbo) {
        getBuffer().allocate(fbo.getWidth(), fbo.getHeight());
        getBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void delete() {
        getBuffer().delete();
    }
}
