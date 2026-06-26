package org.saar.lwjgl.opengl.fbo.exceptions;

public class FboIncompleteAttachmentException extends FrameBufferException {

    public FboIncompleteAttachmentException() {
    }

    public FboIncompleteAttachmentException(String message) {
        super(message);
    }

    public FboIncompleteAttachmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboIncompleteAttachmentException(Throwable cause) {
        super(cause);
    }

    public FboIncompleteAttachmentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
