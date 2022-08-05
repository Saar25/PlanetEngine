package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.index.ColourAttachmentIndex;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.texture.MutableTexture2D;

public class ColourAttachment extends AttachmentBase implements Attachment, IColourAttachment {

    private final ColourAttachmentIndex index;

    public ColourAttachment(ColourAttachmentIndex index, AttachmentBuffer buffer) {
        super(buffer);
        this.index = index;
    }

    public ColourAttachment(int index, AttachmentBuffer buffer) {
        this(new ColourAttachmentIndex(index), buffer);
    }

    public static ColourAttachment withTexture(int index, MutableTexture2D texture, ColourFormatType iFormat) {
        final AttachmentBuffer buffer = new AttachmentTextureBuffer(texture, iFormat.get());
        return new ColourAttachment(new ColourAttachmentIndex(index), buffer);
    }

    public static ColourAttachment withRenderBuffer(int index, RenderBuffer renderBuffer, ColourFormatType iFormat) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(renderBuffer, iFormat.get());
        final ColourAttachmentIndex attachmentIndex = new ColourAttachmentIndex(index);
        return new ColourAttachment(attachmentIndex, buffer);
    }

    public static ColourAttachment withRenderBuffer(int index, ColourFormatType iFormat) {
        return withRenderBuffer(index, RenderBuffer.create(), iFormat);
    }

    @Override
    public ColourAttachmentIndex getIndex() {
        return this.index;
    }
}
