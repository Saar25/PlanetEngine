package org.saar.lwjgl.opengl.fbos;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.saar.lwjgl.opengl.fbos.exceptions.*;

public final class FboStatus {

    private static final String MESSAGE = "Framebuffer creation failed";

    private FboStatus() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static void ensureStatus(int status) throws FrameBufferException {
        switch (status) {
            case GL30.GL_FRAMEBUFFER_COMPLETE:
                break;
            case GL30.GL_FRAMEBUFFER_UNDEFINED:
                throw new FboUndefinedException(MESSAGE);
            case GL30.GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT:
                throw new FboIncompleteAttachmentException(MESSAGE);
            case GL30.GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT:
                throw new FboIncompleteMissingAttachmentException(MESSAGE);
            case GL30.GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER:
                throw new FboIncompleteDrawBufferException(MESSAGE);
            case GL30.GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER:
                throw new FboIncompleteReadBufferException(MESSAGE);
            case GL30.GL_FRAMEBUFFER_UNSUPPORTED:
                throw new FboUnsupportedException(MESSAGE);
            case GL30.GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE:
                throw new FboIncompleteMultisampleException(MESSAGE);
            case GL32.GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS:
                throw new FboIncompleteLayerTargetsException(MESSAGE);
        }
    }

}
