package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.textures.Texture;

public class RenderBufferAttachment extends AbstractAttachment implements Attachment {

    private final RenderBuffer renderBuffer;
    private final FormatType format;

    private RenderBufferAttachment(AttachmentType type, int attachmentIndex, RenderBuffer renderBuffer, FormatType format) {
        super(type, attachmentIndex);
        this.renderBuffer = renderBuffer;
        this.format = format;
    }

    /**
     * Creates a new colour render buffer attachment
     *
     * @return the created depth attachment
     */
    public static RenderBufferAttachment ofColour(int index, FormatType format) {
        final RenderBuffer renderBuffer = RenderBuffer.create();
        return new RenderBufferAttachment(AttachmentType.COLOUR,
                index, renderBuffer, format);
    }

    /**
     * Creates a new depth render buffer attachment
     *
     * @return the created depth attachment
     */
    public static RenderBufferAttachment ofDepth(FormatType format) {
        final RenderBuffer renderBuffer = RenderBuffer.create();
        return new RenderBufferAttachment(AttachmentType.DEPTH,
                0, renderBuffer, format);
    }

    public RenderBuffer getRenderBuffer() {
        return renderBuffer;
    }

    @Override
    public Texture getTexture() {
        return Texture.NULL;
    }

    @Override
    public void init(ReadOnlyFbo fbo) {
        getRenderBuffer().loadStorage(fbo.getWidth(), fbo.getHeight(), format);
        getRenderBuffer().attachToFbo(getAttachmentPoint());
    }

    @Override
    public void delete() {
        getRenderBuffer().delete();
    }
}
