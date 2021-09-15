package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;

public class DepthAttachment extends AttachmentBase implements Attachment {

    public DepthAttachment(AttachmentIndex index, AttachmentBuffer buffer) {
        super(index, buffer);
    }

    public static DepthAttachment withTexture(Texture texture, DepthFormatType iFormat) {
        final AttachmentTextureBuffer buffer = new AttachmentTextureBuffer(texture,
                iFormat.get(), FormatType.DEPTH_COMPONENT, DataType.U_BYTE);
        return new DepthAttachment(AttachmentIndex.ofDepth(), buffer);
    }

    public static DepthAttachment withRenderBuffer(RenderBuffer renderBuffer, DepthFormatType iFormat) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(renderBuffer, iFormat.get());
        return new DepthAttachment(AttachmentIndex.ofDepth(), buffer);
    }

    public static DepthAttachment withRenderBuffer(DepthFormatType iFormat) {
        return withRenderBuffer(RenderBuffer.create(), iFormat);
    }
}
