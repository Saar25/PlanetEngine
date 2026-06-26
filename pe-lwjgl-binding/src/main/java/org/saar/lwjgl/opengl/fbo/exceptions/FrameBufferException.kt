package org.saar.lwjgl.opengl.fbo.exceptions;

public class FrameBufferException extends RuntimeException {

    public FrameBufferException() {
    }

    public FrameBufferException(String message) {
        super(message);
    }

    public FrameBufferException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameBufferException(Throwable cause) {
        super(cause);
    }

    public FrameBufferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
