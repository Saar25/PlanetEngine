package org.saar.lwjgl.opengl.exceptions;

public class VboTooSmallException extends RuntimeException {

    public VboTooSmallException() {
    }

    public VboTooSmallException(String message) {
        super(message);
    }

    public VboTooSmallException(String message, Throwable cause) {
        super(message, cause);
    }

    public VboTooSmallException(Throwable cause) {
        super(cause);
    }

    public VboTooSmallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
