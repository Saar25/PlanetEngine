package org.saar.lwjgl.opengl.fbos.exceptions;

public class FboUndefinedException extends FrameBufferException {

    public FboUndefinedException() {
    }

    public FboUndefinedException(String message) {
        super(message);
    }

    public FboUndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboUndefinedException(Throwable cause) {
        super(cause);
    }

    public FboUndefinedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
