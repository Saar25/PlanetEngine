package org.saar.lwjgl.opengl.fbos;

import org.lwjgl.opengl.GL11;
import org.saar.lwjgl.glfw.window.Window;
import org.saar.lwjgl.opengl.fbos.attachment.Attachment;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentType;
import org.saar.lwjgl.opengl.fbos.exceptions.FrameBufferException;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.utils.GlBuffer;
import org.saar.lwjgl.opengl.utils.GlUtils;

import java.util.Collections;
import java.util.List;

public class ScreenFbo implements IFbo {

    private static final ScreenFbo instance = new ScreenFbo();

    private final List<Attachment> drawAttachments = Collections.singletonList(
            new Attachment() {
                @Override
                public int getAttachmentPoint() {
                    return GL11.GL_BACK;
                }

                @Override
                public AttachmentType getAttachmentType() {
                    return AttachmentType.COLOUR;
                }

                @Override
                public Texture getTexture() {
                    return null;
                }

                @Override
                public void init(Fbo fbo) {

                }

                @Override
                public void delete() {

                }
            }
    );

    private ScreenFbo() {

    }

    public static ScreenFbo getInstance() {
        return instance;
    }

    private Fbo getFbo() {
        return Fbo.NULL;
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
        return Window.current().getWidth();
    }

    @Override
    public int getHeight() {
        return Window.current().getHeight();
    }

    @Override
    public void bind() {
        bind(FboTarget.FRAMEBUFFER);
    }

    @Override
    public void bind(FboTarget target) {
        getFbo().bind(target);
        GlUtils.setViewport(0, 0, getWidth(), getHeight());
        switch (target) {
            case DRAW_FRAMEBUFFER:
                GL11.glDrawBuffer(GL11.GL_BACK);
                break;
            case READ_FRAMEBUFFER:
                GL11.glReadBuffer(GL11.GL_NONE);
                break;
            default:
        }
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
        // Cannot delete screen fbo
    }

    @Override
    public void ensureStatus() throws FrameBufferException {
        getFbo().ensureStatus();
    }
}
