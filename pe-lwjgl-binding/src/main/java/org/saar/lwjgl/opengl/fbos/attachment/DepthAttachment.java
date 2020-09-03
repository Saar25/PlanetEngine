package org.saar.lwjgl.opengl.fbos.attachment;

import attachment.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;

public class DepthAttachment implements IDepthAttachment, Attachment {

    private final AttachmentBuffer buffer;

    public DepthAttachment(AttachmentBuffer buffer) {
        this.buffer = buffer;
    }

    public static DepthAttachment withTexture(Texture texture) {
        final AttachmentBuffer buffer = new AttachmentTextureBuffer(texture);
        return new DepthAttachment(buffer);
    }

    public static DepthAttachment withTexture() {
        final Texture texture = Texture.create(TextureTarget.TEXTURE_2D);
        return DepthAttachment.withTexture(texture);
    }

    public static DepthAttachment withRenderBuffer(RenderBuffer texture) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(texture);
        return new DepthAttachment(buffer);
    }

    public static DepthAttachment withRenderBuffer() {
        final RenderBuffer texture = RenderBuffer.create();
        return DepthAttachment.withRenderBuffer(texture);
    }

    private AttachmentBuffer getBuffer() {
        return this.buffer;
    }

    @Override
    public int getAttachmentPoint() {
        return AttachmentType.DEPTH.get();
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
