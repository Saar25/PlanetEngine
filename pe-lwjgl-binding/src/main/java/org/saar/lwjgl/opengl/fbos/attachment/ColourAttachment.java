package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.ColourFormatType;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;

public class ColourAttachment extends AttachmentBase implements Attachment {

    public ColourAttachment(AttachmentIndex index, AttachmentBuffer buffer) {
        super(index, buffer);
    }

    public static ColourAttachment withTexture(int index, Texture texture, ColourFormatType iFormat) {
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
}
