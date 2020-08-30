package org.saar.lwjgl.opengl.fbos.exceptions;

public class FboIncompleteMultisampleException extends FrameBufferException {

    public FboIncompleteMultisampleException() {
    }

    public FboIncompleteMultisampleException(String message) {
        super(message);
    }

    public FboIncompleteMultisampleException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboIncompleteMultisampleException(Throwable cause) {
        super(cause);
    }

    public FboIncompleteMultisampleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
