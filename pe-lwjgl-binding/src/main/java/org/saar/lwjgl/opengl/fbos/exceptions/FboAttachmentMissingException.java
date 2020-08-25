package org.saar.lwjgl.opengl.fbos.exceptions;

public class FboAttachmentMissingException extends FrameBufferException {

    public FboAttachmentMissingException() {
    }

    public FboAttachmentMissingException(String message) {
        super(message);
    }

    public FboAttachmentMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboAttachmentMissingException(Throwable cause) {
        super(cause);
    }

    public FboAttachmentMissingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
