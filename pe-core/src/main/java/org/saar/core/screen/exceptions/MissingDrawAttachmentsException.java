package org.saar.core.screen.exceptions;

public class MissingDrawAttachmentsException extends RuntimeException {

    public MissingDrawAttachmentsException() {
    }

    public MissingDrawAttachmentsException(String message) {
        super(message);
    }

    public MissingDrawAttachmentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingDrawAttachmentsException(Throwable cause) {
        super(cause);
    }

    public MissingDrawAttachmentsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
