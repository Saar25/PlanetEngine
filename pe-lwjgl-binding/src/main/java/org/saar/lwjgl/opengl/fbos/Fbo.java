package org.saar.lwjgl.opengl.fbos;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;
import org.saar.lwjgl.opengl.fbos.exceptions.FboAttachmentMissingException;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlConfigs;
import org.saar.lwjgl.opengl.utils.GlUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Fbo implements IFbo {

    public static final Fbo NULL = new Fbo(0, 0, 0);

    private static int boundFbo = 0;

    private final int id;

    private final int width;
    private final int height;

    private List<Attachment> drawAttachments = Collections.emptyList();
    private Attachment readAttachment;

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

    /**
     * Returns whether the fbo has the same dimensions as given
     *
     * @param width  the width
     * @param height the height
     * @return true if has the same size false otherwise
     */
    public boolean isSized(int width, int height) {
        return getWidth() == width && getHeight() == height;
    }

    public void blitToScreen() {
        blitFbo(ScreenFbo.getInstance());
    }

    public void blitFbo(IFbo fbo) {
        blitFbo(fbo, MagFilterParameter.NEAREST, GlBuffer.COLOUR, GlBuffer.DEPTH);
    }

    @Override
    public void blitFbo(IFbo fbo, MagFilterParameter filter, GlBuffer... buffers) {
        bind(FboTarget.READ_FRAMEBUFFER);
        fbo.bind(FboTarget.DRAW_FRAMEBUFFER);
        blitFramebuffer(0, 0, getWidth(), getHeight(), 0, 0,
                fbo.getWidth(), fbo.getHeight(), filter, buffers);
    }

    private void blitFramebuffer(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2,
                                 MagFilterParameter filter, GlBuffer... buffers) {
        GL30.glBlitFramebuffer(x1, y1, w1, h1, x2, y2, w2, h2, GlBuffer.getValue(buffers), filter.get());
    }

    /**
     * Sets the read attachments of the fbo
     *
     * @param attachment the read attachment
     */
    public void setReadAttachment(Attachment attachment) {
        this.readAttachment = attachment;
    }

    /**
     * Sets the draw attachments of the fbo
     *
     * @param attachments the draw attachments
     */
    public void setDrawAttachments(Attachment... attachments) {
        this.drawAttachments = Arrays.asList(attachments);
    }

    private Attachment getReadAttachment() {
        if (this.readAttachment != null) {
            return this.readAttachment;
        }
        throw new FboAttachmentMissingException("Read attachment is not set");
    }

    private void readAttachment() {
        GL11.glReadBuffer(getReadAttachment().getAttachmentPoint());
    }

    private List<Attachment> getDrawAttachments() {
        return this.drawAttachments;
    }

    private void drawAttachments() {
        final int[] buffer = new int[getDrawAttachments().size()];
        for (int i = 0; i < getDrawAttachments().size(); i++) {
            final Attachment attachment = getDrawAttachments().get(i);
            buffer[i] = attachment.getAttachmentPoint();
        }
        setDrawBuffers(buffer);
    }

    private void setDrawBuffers(int... buffers) {
        if (buffers.length == 1) {
            GL11.glDrawBuffer(buffers[0]);
        } else {
            GL20.glDrawBuffers(buffers);
        }
    }

    private void setViewport() {
        GlUtils.setViewport(0, 0, getWidth(), getHeight());
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
    public void bind(FboTarget target) {
        if (!GlConfigs.CACHE_STATE || boundFbo != id) {
            this.bind0(target);
        }
        switch (target) {
            case DRAW_FRAMEBUFFER:
                drawAttachments();
                setViewport();
                break;
            case FRAMEBUFFER:
                setViewport();
                break;
            case READ_FRAMEBUFFER:
                readAttachment();
                break;
            default:
        }
    }

    @Override
    public void bind() {
        this.bind(FboTarget.FRAMEBUFFER);
    }

    @Override
    public void unbind(FboTarget target) {
        Fbo.NULL.bind(target);
    }

    @Override
    public void unbind() {
        this.unbind(FboTarget.FRAMEBUFFER);
    }

    @Override
    public void delete() {
        GL30.glDeleteFramebuffers(id);
    }

    private void bind0(FboTarget target) {
        GL30.glBindFramebuffer(target.get(), id);
        Fbo.boundFbo = id;
    }
}
