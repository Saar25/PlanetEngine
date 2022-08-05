package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.texture.MutableTexture2D;

public class DepthAttachment extends AttachmentBase implements Attachment {

    private final AttachmentIndex index = AttachmentIndex.ofDepth();

    public DepthAttachment(AttachmentBuffer buffer) {
        super(buffer);
    }

    public static DepthAttachment withTexture(MutableTexture2D texture, DepthFormatType iFormat) {
        final AttachmentTextureBuffer buffer = new AttachmentTextureBuffer(texture,
                iFormat.get(), FormatType.DEPTH_COMPONENT, DataType.U_BYTE);
        return new DepthAttachment(buffer);
    }

    public static DepthAttachment withRenderBuffer(RenderBuffer renderBuffer, DepthFormatType iFormat) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(renderBuffer, iFormat.get());
        return new DepthAttachment(buffer);
    }

    public static DepthAttachment withRenderBuffer(DepthFormatType iFormat) {
        return withRenderBuffer(RenderBuffer.create(), iFormat);
    }

    @Override
    public AttachmentIndex getIndex() {
        return this.index;
    }
}
