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
    public void blitFbo(DrawableFbo fbo) {
        getFbo().blitFbo(fbo);
    }

    @Override
    public void blitFbo(DrawableFbo fbo, MagFilterParameter filter, GlBuffer... buffers) {
        getFbo().blitFbo(fbo, filter, buffers);
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
