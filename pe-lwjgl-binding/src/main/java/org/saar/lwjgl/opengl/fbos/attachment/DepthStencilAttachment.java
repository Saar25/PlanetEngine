package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.DepthStencilFormatType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex;
import org.saar.lwjgl.opengl.fbos.attachment.index.BasicAttachmentIndex;
import org.saar.lwjgl.opengl.texture.MutableTexture2D;

public class DepthStencilAttachment extends AttachmentBase implements Attachment {

    private final AttachmentIndex index = new BasicAttachmentIndex(AttachmentType.DEPTH_STENCIL);

    public DepthStencilAttachment(AttachmentBuffer buffer) {
        super(buffer);
    }

    public static DepthStencilAttachment withTexture(MutableTexture2D texture, DepthStencilFormatType iFormat, DataType dataType) {
        final AttachmentTextureBuffer buffer = new AttachmentTextureBuffer(
                texture, iFormat.get(), FormatType.DEPTH_STENCIL, dataType);
        return new DepthStencilAttachment(buffer);
    }

    @Override
    public AttachmentIndex getIndex() {
        return this.index;
    }
}
