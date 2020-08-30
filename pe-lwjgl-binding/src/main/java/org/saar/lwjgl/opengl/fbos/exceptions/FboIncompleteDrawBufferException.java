package org.saar.lwjgl.opengl.fbos.exceptions;

public class FboIncompleteDrawBufferException extends FrameBufferException {

    public FboIncompleteDrawBufferException() {
    }

    public FboIncompleteDrawBufferException(String message) {
        super(message);
    }

    public FboIncompleteDrawBufferException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboIncompleteDrawBufferException(Throwable cause) {
        super(cause);
    }

    public FboIncompleteDrawBufferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
