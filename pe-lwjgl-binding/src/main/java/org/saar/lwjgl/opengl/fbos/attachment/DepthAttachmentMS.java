package org.saar.lwjgl.opengl.fbos.attachment;

import attachment.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;

public class DepthAttachmentMS implements IDepthAttachment, Attachment {

    private final AttachmentBuffer buffer;
    private final int samples;

    private DepthAttachmentMS(AttachmentBuffer buffer, int samples) {
        this.buffer = buffer;
        this.samples = samples;
    }

    public static DepthAttachmentMS withTexture(Texture texture, int samples) {
        final AttachmentBuffer buffer = new AttachmentTextureBuffer(texture);
        return new DepthAttachmentMS(buffer, samples);
    }

    public static DepthAttachmentMS withTexture(int samples) {
        final Texture texture = Texture.create(TextureTarget.TEXTURE_2D);
        return DepthAttachmentMS.withTexture(texture, samples);
    }

    public static DepthAttachmentMS withRenderBuffer(RenderBuffer texture, int samples) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(texture);
        return new DepthAttachmentMS(buffer, samples);
    }

    public static DepthAttachmentMS withRenderBuffer(int samples) {
        final RenderBuffer texture = RenderBuffer.create();
        return DepthAttachmentMS.withRenderBuffer(texture, samples);
    }

    private AttachmentBuffer getBuffer() {
        return this.buffer;
    }

    @Override
    public int getAttachmentPoint() {
        return getAttachmentType().get();
    }

    @Override
    public AttachmentType getAttachmentType() {
        return AttachmentType.COLOUR;
    }

    @Override
    public Texture getTexture() {
        return null;
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
