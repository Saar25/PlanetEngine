package org.saar.lwjgl.opengl.fbos;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;
import org.saar.lwjgl.opengl.fbos.attachment.colour.IColourAttachment;
import org.saar.lwjgl.opengl.fbos.exceptions.FboAttachmentMissingException;
import org.saar.lwjgl.opengl.fbos.exceptions.FrameBufferException;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlConfigs;
import org.saar.lwjgl.opengl.utils.GlUtils;

import java.util.Collections;
import java.util.List;

public class Fbo implements IFbo {

    public static final Fbo NULL = new Fbo(0, 0, 0);

    private final int id;

    private int width;
    private int height;

    private List<Attachment> drawAttachments = Collections.emptyList();
    private IColourAttachment readAttachment;

    private Fbo(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public static Fbo create(int width, int height) {
        final int id = GL30.glGenFramebuffers();
        return new Fbo(id, width, height);
    }

    /**
     * Adds an attachment to the fbo
     *
     * @param attachment the attachment to add
     */
    public void addAttachment(Attachment attachment) {
        bind();
        attachment.init(this);
    }

    public void blitToScreen() {
        blitFbo(ScreenFbo.getInstance());
    }

    @Override
    public void blitFbo(DrawableFbo fbo) {
        blitFbo(fbo, MagFilterParameter.NEAREST, GlBuffer.COLOUR, GlBuffer.DEPTH);
    }

    @Override
    public void blitFbo(DrawableFbo fbo, MagFilterParameter filter, GlBuffer... buffers) {
        bindAsRead();
        fbo.bindAsDraw();
        blitFramebuffer(fbo.getWidth(), fbo.getHeight(), filter, buffers);
    }

    public void blitFramebuffer(int w, int h, MagFilterParameter filter, GlBuffer... buffers) {
        blitFramebuffer(getWidth(), getHeight(), w, h, filter, buffers);
    }

    public void blitFramebuffer(int w1, int h1, int w2, int h2, MagFilterParameter filter, GlBuffer... buffers) {
        blitFramebuffer(0, 0, w1, h1, 0, 0, w2, h2, filter, buffers);
    }

    @Override
    public void blitFramebuffer(int x1, int y1, int w1, int h1, int x2, int y2, int w2,
                                int h2, MagFilterParameter filter, GlBuffer... buffers) {
        GL30.glBlitFramebuffer(x1, y1, w1, h1, x2, y2, w2, h2, GlBuffer.getValue(buffers), filter.get());
    }

    /**
     * Sets the read attachments of the fbo
     *
     * @param attachment the read attachment
     */
    public void setReadAttachment(IColourAttachment attachment) {
        bind();
        GL11.glReadBuffer(attachment.getAttachmentPoint());
    }

    private Attachment getReadAttachment() {
        throw new FboAttachmentMissingException("Read attachment is not set");
    }

    /**
     * Sets the draw attachments of the fbo
     *
     * @param attachments the draw attachments
     */
    public void setDrawAttachments(Attachment... attachments) {
        bind();
        setDrawBuffers(attachmentsPoints(attachments));
    }

    private static int[] attachmentsPoints(Attachment... attachments) {
        final int[] buffer = new int[attachments.length];
        for (int i = 0; i < attachments.length; i++) {
            final Attachment attachment = attachments[i];
            buffer[i] = attachment.getAttachmentPoint();
        }
        return buffer;
    }

    private void setDrawBuffers(int... buffers) {
        if (buffers.length == 1) {
            GL11.glDrawBuffer(buffers[0]);
        } else if (buffers.length > 1) {
            GL20.glDrawBuffers(buffers);
        }
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
