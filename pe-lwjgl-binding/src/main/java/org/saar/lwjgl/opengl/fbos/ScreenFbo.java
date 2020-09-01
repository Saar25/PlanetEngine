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
