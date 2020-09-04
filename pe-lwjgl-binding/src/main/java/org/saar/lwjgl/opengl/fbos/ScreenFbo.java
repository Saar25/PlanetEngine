package org.saar.lwjgl.opengl.fbos;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.fbos.exceptions.FrameBufferException;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

public class ScreenFbo implements IFbo {

    private static final ScreenFbo instance = new ScreenFbo();

    private ScreenFbo() {
    }

    public static ScreenFbo getInstance() {
        return ScreenFbo.instance;
    }

    private Fbo getFbo() {
        return Fbo.NULL;
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
    public void blitFramebuffer(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2,
                                MagFilterParameter filter, GlBuffer... buffers) {
        getFbo().blitFramebuffer(x1, y1, w1, h1, x2, y2, w2, h2, filter, buffers);
    }

    @Override
    public void resize(int width, int height) {
        getFbo().resize(width, height);
    }

    @Override
    public int getWidth() {
        return Window.current().getWidth();
    }

    @Override
    public int getHeight() {
        return Window.current().getHeight();
    }

    private void setViewport() {
        GlUtils.setViewport(0, 0, getWidth(), getHeight());
    }

    @Override
    public void bindAsRead() {
        getFbo().bind(FboTarget.READ_FRAMEBUFFER);
        GL11.glReadBuffer(GL11.GL_NONE);
        setViewport();
    }

    @Override
    public void bindAsDraw() {
        getFbo().bind(FboTarget.DRAW_FRAMEBUFFER);
        GL11.glDrawBuffer(GL11.GL_BACK);
    }

    @Override
    public void bind() {
        getFbo().bind(FboTarget.FRAMEBUFFER);
        setViewport();
    }

    @Override
    public void unbind() {
        getFbo().unbind();
    }

    @Override
    public void delete() {
        // Cannot delete screen fbo
    }

    @Override
    public void ensureStatus() throws FrameBufferException {
        getFbo().ensureStatus();
    }
}
