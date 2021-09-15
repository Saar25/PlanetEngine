package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;

public class ColourAttachment implements Attachment {

    private final int index;
    private final AttachmentBuffer buffer;

    public ColourAttachment(int index, AttachmentBuffer buffer) {
        this.index = index;
        this.buffer = buffer;
    }

    public static ColourAttachment withTexture(int index, Texture texture, ColourFormatType iFormat) {
        return new ColourAttachment(index, new AttachmentTextureBuffer(texture, iFormat.get(), FormatType.RGBA, DataType.BYTE));
    }

    public static ColourAttachment withRenderBuffer(int index, RenderBuffer renderBuffer, ColourFormatType iFormat) {
        return new ColourAttachment(index, new AttachmentRenderBuffer(renderBuffer, iFormat.get()));
    }

    public static ColourAttachment withRenderBuffer(int index, ColourFormatType iFormat) {
        return ColourAttachment.withRenderBuffer(index, RenderBuffer.create(), iFormat);
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
    public void initMS(ReadOnlyFbo fbo, int samples) {
        getBuffer().allocateMultisample(fbo.getWidth(), fbo.getHeight(), samples);
        getBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void delete() {
        getBuffer().delete();
    }
}
