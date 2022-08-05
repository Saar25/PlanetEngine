package org.saar.lwjgl.opengl.fbos;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;
import org.saar.lwjgl.opengl.fbos.attachment.Attachments;
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment;
import org.saar.lwjgl.opengl.fbos.exceptions.FrameBufferException;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlConfigs;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class Fbo implements IFbo {

    public static final Fbo NULL = new Fbo(0, 0, 0);

    private final int id;

    private int width;
    private int height;

    private Fbo(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public static Fbo create(int width, int height) {
        final int id = GL30.glGenFramebuffers();
        return new Fbo(id, width, height);
    }

    @Override
    public void addAttachment(Attachment attachment) {
        bind();
        attachment.init();
    }

    @Override
    public void setReadAttachment(ColourAttachment attachment) {
        bind();
        Attachments.readAttachment(attachment);
    }

    @Override
    public void setDrawAttachments(Attachment... attachments) {
        bind();
        Attachments.drawAttachments(attachments);
    }

    public void blitToScreen() {
        final ScreenFbo other = ScreenFbo.getInstance();
        blitFramebuffer(other, FboBlitFilter.LINEAR, GlBuffer.COLOUR);
    }

    public void blitFramebuffer(DrawableFbo fbo, FboBlitFilter filter, GlBuffer... buffers) {
        fbo.bindAsDraw();
        blitFramebuffer(fbo.getWidth(), fbo.getHeight(), filter, buffers);
    }

    public void blitFramebuffer(int w, int h, FboBlitFilter filter, GlBuffer... buffers) {
        blitFramebuffer(getWidth(), getHeight(), w, h, filter, buffers);
    }

    public void blitFramebuffer(int w1, int h1, int w2, int h2, FboBlitFilter filter, GlBuffer... buffers) {
        blitFramebuffer(0, 0, w1, h1, 0, 0, w2, h2, filter, buffers);
    }

    @Override
    public void blitFramebuffer(int x1, int y1, int w1, int h1, int x2, int y2, int w2,
                                int h2, FboBlitFilter filter, GlBuffer... buffers) {
        bindAsRead();
        GL30.glBlitFramebuffer(x1, y1, w1, h1, x2, y2, w2, h2, GlBuffer.getValue(buffers), filter.get());
    }

    private void setViewport() {
        GlUtils.setViewport(0, 0, getWidth(), getHeight());
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void bindAsRead() {
        bind(FboTarget.READ_FRAMEBUFFER);
    }

    @Override
    public void bindAsDraw() {
        bind(FboTarget.DRAW_FRAMEBUFFER);
        setViewport();
    }

    @Override
    public void bind() {
        bind(FboTarget.FRAMEBUFFER);
        setViewport();
    }

    @Override
    public void unbind() {
        unbind(FboTarget.FRAMEBUFFER);
    }

    @Override
    public void delete() {
        GL30.glDeleteFramebuffers(id);
    }

    @Override
    public void ensureStatus() throws FrameBufferException {
        bind();
        final int status = GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER);
        FboStatus.ensureStatus(status);
    }

    public void bind(FboTarget target) {
        if (!GlConfigs.CACHE_STATE || BoundFbo.isBound(target, this.id)) {
            bind0(target);
        }
    }

    public void unbind(FboTarget target) {
        Fbo.NULL.bind(target);
    }

    private void bind0(FboTarget target) {
        GL30.glBindFramebuffer(target.get(), this.id);
        BoundFbo.set(target, this.id);
    }
}
