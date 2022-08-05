package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentRenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.buffer.AttachmentTextureBuffer;
import org.saar.lwjgl.opengl.objects.rbos.RenderBuffer;
import org.saar.lwjgl.opengl.texture.MutableTexture2D;

public class StencilAttachment extends AttachmentBase implements Attachment {

    private final AttachmentIndex index = AttachmentIndex.ofStencil();

    public StencilAttachment(AttachmentBuffer buffer) {
        super(buffer);
    }

    public static StencilAttachment withTexture(MutableTexture2D texture) {
        final AttachmentBuffer buffer = new AttachmentTextureBuffer(texture,
                InternalFormat.STENCIL_INDEX8, FormatType.STENCIL_INDEX, DataType.U_BYTE);
        return new StencilAttachment(buffer);
    }

    public static StencilAttachment withRenderBuffer(RenderBuffer renderBuffer) {
        final AttachmentBuffer buffer = new AttachmentRenderBuffer(
                renderBuffer, InternalFormat.STENCIL_INDEX8);
        return new StencilAttachment(buffer);
    }

    public static StencilAttachment withRenderBuffer() {
        return withRenderBuffer(RenderBuffer.create());
    }

    @Override
    public AttachmentIndex getIndex() {
        return this.index;
    }
}
