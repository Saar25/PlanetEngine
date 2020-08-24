package org.saar.lwjgl.opengl.fbos.exceptions;

public class FboIncompleteMissingAttachmentException extends FrameBufferException {

    public FboIncompleteMissingAttachmentException() {
    }

    public FboIncompleteMissingAttachmentException(String message) {
        super(message);
    }

    public FboIncompleteMissingAttachmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboIncompleteMissingAttachmentException(Throwable cause) {
        super(cause);
    }

    public FboIncompleteMissingAttachmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
