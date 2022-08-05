package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.texture.MutableTexture2D;

public class ColourAttachment extends AttachmentBase implements Attachment {

    private final AttachmentIndex index;

    public ColourAttachment(AttachmentIndex index, AttachmentBuffer buffer) {
        super(buffer);
        this.index = index;
    }

    public ColourAttachment(int index, AttachmentBuffer buffer) {
        super(buffer);
        this.index = AttachmentIndex.ofColour(index);
    }

    public static ColourAttachment withTexture(int index, MutableTexture2D texture, ColourFormatType iFormat) {
        final AttachmentBuffer buffer = new AttachmentTextureBuffer(texture, iFormat.get());
        return new ColourAttachment(AttachmentIndex.ofColour(index), buffer);
    }

    public static ColourAttachment withRenderBuffer(int index, RenderBuffer renderBuffer, ColourFormatType iFormat) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(renderBuffer, iFormat.get());
        return new ColourAttachment(AttachmentIndex.ofColour(index), buffer);
    }

    public static ColourAttachment withRenderBuffer(int index, ColourFormatType iFormat) {
        return withRenderBuffer(index, RenderBuffer.create(), iFormat);
    }

    @Override
    public AttachmentIndex getIndex() {
        return this.index;
    }
}
