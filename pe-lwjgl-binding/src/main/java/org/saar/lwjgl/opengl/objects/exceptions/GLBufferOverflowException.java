package org.saar.lwjgl.opengl.objects.exceptions;

public class GLBufferOverflowException extends RuntimeException {

    public GLBufferOverflowException() {
    }

    public GLBufferOverflowException(String message) {
        super(message);
    }

    public GLBufferOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public GLBufferOverflowException(Throwable cause) {
        super(cause);
    }

    public GLBufferOverflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
