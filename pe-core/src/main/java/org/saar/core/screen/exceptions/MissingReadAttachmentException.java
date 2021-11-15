package org.saar.core.screen.exceptions;

public class MissingReadAttachmentException extends RuntimeException {

    public MissingReadAttachmentException() {
    }

    public MissingReadAttachmentException(String message) {
        super(message);
    }

    public MissingReadAttachmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingReadAttachmentException(Throwable cause) {
        super(cause);
    }

    public MissingReadAttachmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
