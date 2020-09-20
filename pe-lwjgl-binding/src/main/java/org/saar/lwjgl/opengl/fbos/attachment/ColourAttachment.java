package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.IInternalFormat;
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

    public static ColourAttachment withTexture(int index, Texture texture, IInternalFormat iFormat,
                                               FormatType format, DataType dataType) {
        return new ColourAttachment(index, new AttachmentTextureBuffer(texture, iFormat, format, dataType));
    }

    public static ColourAttachment withRenderBuffer(int index, RenderBuffer renderBuffer, IInternalFormat iFormat) {
        return new ColourAttachment(index, new AttachmentRenderBuffer(renderBuffer, iFormat));
    }

    public static ColourAttachment withRenderBuffer(int index, IInternalFormat iFormat) {
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
