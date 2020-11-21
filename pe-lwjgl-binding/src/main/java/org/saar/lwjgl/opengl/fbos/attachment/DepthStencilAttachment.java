package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthStencilFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;

public class DepthStencilAttachment implements Attachment {

    private final AttachmentBuffer buffer;

    public DepthStencilAttachment(AttachmentBuffer buffer) {
        this.buffer = buffer;
    }

    public static DepthStencilAttachment withTexture(Texture texture, DepthStencilFormatType iFormat, DataType dataType) {
        final AttachmentTextureBuffer buffer = new AttachmentTextureBuffer(
                texture, iFormat.get(), FormatType.DEPTH_STENCIL, dataType);
        return new DepthStencilAttachment(buffer);
    }

    public static DepthStencilAttachment withRenderBuffer(RenderBuffer renderBuffer, DepthStencilFormatType iFormat) {
        return new DepthStencilAttachment(new AttachmentRenderBuffer(renderBuffer, iFormat.get()));
    }

    public static DepthStencilAttachment withRenderBuffer(DepthStencilFormatType iFormat) {
        return DepthStencilAttachment.withRenderBuffer(RenderBuffer.create(), iFormat);
    }

    private AttachmentBuffer getBuffer() {
        return this.buffer;
    }

    @Override
    public int getAttachmentPoint() {
        return AttachmentType.DEPTH_STENCIL.get();
    }

    @Override
    public void init(ReadOnlyFbo fbo) {
        getBuffer().allocate(fbo.getWidth(), fbo.getHeight());
        getBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void initMS(ReadOnlyFbo fbo, int samples) {
        getBuffer().allocateMultisample(fbo.getWidth(), fbo.getHeight(), samples);
        getBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void delete() {
        getBuffer().delete();
    }
}
