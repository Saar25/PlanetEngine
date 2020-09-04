package org.saar.lwjgl.opengl.fbos.attachment.depth;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentMS;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;

public class DepthAttachmentMS implements IDepthAttachment, AttachmentMS {

    private final AttachmentBuffer buffer;
    private final int samples;

    public DepthAttachmentMS(AttachmentBuffer buffer, int samples) {
        this.buffer = buffer;
        this.samples = samples;
    }

    public static DepthAttachmentMS withTexture(DepthFormatType format, Texture texture, int samples) {
        final AttachmentBuffer buffer = new AttachmentTextureBuffer(texture,
                FormatType.DEPTH_COMPONENT, format, DataType.U_BYTE);
        return new DepthAttachmentMS(buffer, samples);
    }

    public static DepthAttachmentMS withTexture(DepthFormatType format, int samples) {
        final Texture texture = Texture.create(TextureTarget.TEXTURE_2D);
        return DepthAttachmentMS.withTexture(format, texture, samples);
    }

    public static DepthAttachmentMS withRenderBuffer(DepthFormatType format, RenderBuffer texture, int samples) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(texture, format);
        return new DepthAttachmentMS(buffer, samples);
    }

    public static DepthAttachmentMS withRenderBuffer(DepthFormatType format, int samples) {
        final RenderBuffer texture = RenderBuffer.create();
        return DepthAttachmentMS.withRenderBuffer(format, texture, samples);
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
        getBuffer().allocateMultisample(fbo.getWidth(), fbo.getHeight(), this.samples);
        getBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void delete() {
        getBuffer().delete();
    }
}
