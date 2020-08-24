package org.saar.lwjgl.opengl.fbos.exceptions;

public class FboUnsupportedException extends FrameBufferException {

    public FboUnsupportedException() {
    }

    public FboUnsupportedException(String message) {
        super(message);
    }

    public FboUnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboUnsupportedException(Throwable cause) {
        super(cause);
    }

    public FboUnsupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
