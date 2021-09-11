package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;

public class StencilAttachment implements Attachment {

    private final AttachmentBuffer buffer;

    public StencilAttachment(AttachmentBuffer buffer) {
        this.buffer = buffer;
    }

    public static StencilAttachment withTexture(Texture texture) {
        final AttachmentTextureBuffer buffer = new AttachmentTextureBuffer(
                texture, InternalFormat.STENCIL_INDEX8, FormatType.STENCIL_INDEX, DataType.U_BYTE);
        return new StencilAttachment(buffer);
    }

    public static StencilAttachment withRenderBuffer(RenderBuffer renderBuffer) {
        return new StencilAttachment(new AttachmentRenderBuffer(renderBuffer, InternalFormat.STENCIL_INDEX8));
    }

    public static StencilAttachment withRenderBuffer() {
        return StencilAttachment.withRenderBuffer(RenderBuffer.create());
    }

    private AttachmentBuffer getBuffer() {
        return this.buffer;
    }

    @Override
    public int getAttachmentPoint() {
        return AttachmentType.STENCIL.get();
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
