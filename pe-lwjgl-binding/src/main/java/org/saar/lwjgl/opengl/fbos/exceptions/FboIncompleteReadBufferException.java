package org.saar.lwjgl.opengl.fbos.exceptions;

public class FboIncompleteReadBufferException extends FrameBufferException {

    public FboIncompleteReadBufferException() {
    }

    public FboIncompleteReadBufferException(String message) {
        super(message);
    }

    public FboIncompleteReadBufferException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboIncompleteReadBufferException(Throwable cause) {
        super(cause);
    }

    public FboIncompleteReadBufferException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
