package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.fbos.attachment.Attachment;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentMS;
import org.saar.lwjgl.opengl.fbos.attachment.colour.IColourAttachment;
import org.saar.lwjgl.opengl.fbos.exceptions.FrameBufferException;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.utils.GlBuffer;

public class MultisampledFbo implements IFbo {

    private final Fbo fbo;

    public MultisampledFbo(int width, int height) {
        this.fbo = Fbo.create(width, height);
    }

    private Fbo getFbo() {
        return fbo;
    }

    public void setReadAttachment(IColourAttachment attachment) {
        getFbo().setReadAttachment(attachment);
    }

    public void setDrawAttachments(Attachment... attachments) {
        getFbo().setDrawAttachments(attachments);
    }

    public void addAttachment(AttachmentMS attachment) {
        getFbo().addAttachment(attachment);
    }

    public void blitToScreen() {
        getFbo().blitToScreen();
    }

    @Override
    public void blitFramebuffer(int x1, int y1, int w1, int h1, int x2, int y2, int w2,
                                int h2, MagFilterParameter filter, GlBuffer... buffers) {
        getFbo().blitFramebuffer(x1, y1, w1, h1, x2, y2, w2, h2, filter, buffers);
    }

    @Override
    public void resize(int width, int height) {
        getFbo().resize(width, height);
    }

    @Override
    public int getWidth() {
        return getFbo().getWidth();
    }

    @Override
    public int getHeight() {
        return getFbo().getHeight();
    }

    @Override
    public void bindAsDraw() {
        getFbo().bindAsDraw();
    }

    @Override
    public void bindAsRead() {
        getFbo().bindAsRead();
    }

    @Override
    public void bind() {
        getFbo().bind();
    }

    @Override
    public void unbind() {
        getFbo().unbind();
    }

    @Override
    public void delete() {
        getFbo().delete();
    }

    @Override
    public void ensureStatus() throws FrameBufferException {
        getFbo().ensureStatus();
    }
}
