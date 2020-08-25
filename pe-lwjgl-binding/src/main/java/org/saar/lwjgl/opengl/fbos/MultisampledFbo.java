package org.saar.lwjgl.opengl.fbos;

import org.saar.lwjgl.opengl.fbos.attachment.Attachment;
import org.saar.lwjgl.opengl.fbos.attachment.MultisampledAttachment;
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

    public void setReadAttachment(Attachment attachment) {
        getFbo().setReadAttachment(attachment);
    }

    public void setDrawAttachments(Attachment... attachments) {
        getFbo().setDrawAttachments(attachments);
    }

    public void addAttachment(MultisampledAttachment attachment) {
        getFbo().addAttachment(attachment);
    }

    public void blitToScreen() {
        getFbo().blitToScreen();
    }

    public void blitFbo(IFbo fbo) {
        getFbo().blitFbo(fbo);
    }

    @Override
    public void blitFbo(IFbo fbo, MagFilterParameter filter, GlBuffer... buffers) {
        getFbo().blitFbo(fbo, filter, buffers);
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
    public void bind() {
        getFbo().bind();
    }


    @Override
    public void bind(FboTarget target) {
        getFbo().bind(target);
    }

    @Override
    public void unbind(FboTarget target) {
        getFbo().unbind(target);
    }

    @Override
    public void unbind() {
        getFbo().unbind();
    }

    @Override
    public void delete() {
        getFbo().delete();
    }
}
