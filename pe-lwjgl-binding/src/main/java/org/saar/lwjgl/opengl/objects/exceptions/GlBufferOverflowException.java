package org.saar.lwjgl.opengl.objects.exceptions;

public class GlBufferOverflowException extends RuntimeException {

    public GlBufferOverflowException() {
    }

    public GlBufferOverflowException(String message) {
        super(message);
    }

    public GlBufferOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlBufferOverflowException(Throwable cause) {
        super(cause);
    }

    public GlBufferOverflowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
