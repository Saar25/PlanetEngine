package org.saar.lwjgl.opengl.exceptions;

public class TwoIndexBuffersException extends RuntimeException {

    public TwoIndexBuffersException() {
    }

    public TwoIndexBuffersException(String message) {
        super(message);
    }

    public TwoIndexBuffersException(String message, Throwable cause) {
        super(message, cause);
    }

    public TwoIndexBuffersException(Throwable cause) {
        super(cause);
    }

    public TwoIndexBuffersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
