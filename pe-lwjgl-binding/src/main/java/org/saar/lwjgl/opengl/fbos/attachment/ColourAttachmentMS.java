package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;

public class ColourAttachmentMS implements IColourAttachment, AttachmentMS {

    private final int index;
    private final AttachmentBuffer buffer;
    private final int samples;

    public ColourAttachmentMS(int index, AttachmentBuffer buffer, int samples) {
        this.index = index;
        this.buffer = buffer;
        this.samples = samples;
    }

    public static ColourAttachmentMS withTexture(int index, Texture texture, int samples) {
        final AttachmentBuffer buffer = new AttachmentTextureBuffer(texture);
        return new ColourAttachmentMS(index, buffer, samples);
    }

    public static ColourAttachmentMS withTexture(int index, int samples) {
        final Texture texture = Texture.create(TextureTarget.TEXTURE_2D);
        return ColourAttachmentMS.withTexture(index, texture, samples);
    }

    public static ColourAttachmentMS withRenderBuffer(int index, RenderBuffer texture, int samples) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(texture);
        return new ColourAttachmentMS(index, buffer, samples);
    }

    public static ColourAttachmentMS withRenderBuffer(int index, int samples) {
        final RenderBuffer texture = RenderBuffer.create();
        return ColourAttachmentMS.withRenderBuffer(index, texture, samples);
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
        getBuffer().allocateMultisample(fbo.getWidth(), fbo.getHeight(), this.samples);
        getBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void delete() {
        getBuffer().delete();
    }
}
