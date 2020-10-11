package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;

public class DepthAttachment implements Attachment {

    private final AttachmentBuffer buffer;

    public DepthAttachment(AttachmentBuffer buffer) {
        this.buffer = buffer;
    }

    public static DepthAttachment withTexture(Texture texture, DepthFormatType iFormat, DataType dataType) {
        final AttachmentTextureBuffer buffer = new AttachmentTextureBuffer(
                texture, iFormat, FormatType.DEPTH_COMPONENT, dataType);
        return new DepthAttachment(buffer);
    }

    public static DepthAttachment withRenderBuffer(RenderBuffer renderBuffer, DepthFormatType iFormat) {
        return new DepthAttachment(new AttachmentRenderBuffer(renderBuffer, iFormat));
    }

    public static DepthAttachment withRenderBuffer(DepthFormatType iFormat) {
        return DepthAttachment.withRenderBuffer(RenderBuffer.create(), iFormat);
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
    public void initMS(ReadOnlyFbo fbo, int samples) {
        getBuffer().allocateMultisample(fbo.getWidth(), fbo.getHeight(), samples);
        getBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void delete() {
        getBuffer().delete();
    }
}
