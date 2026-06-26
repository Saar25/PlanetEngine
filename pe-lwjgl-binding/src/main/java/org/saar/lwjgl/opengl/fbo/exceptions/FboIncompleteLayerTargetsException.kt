package org.saar.lwjgl.opengl.fbo.exceptions;

public class FboIncompleteLayerTargetsException extends FrameBufferException {

    public FboIncompleteLayerTargetsException() {
    }

    public FboIncompleteLayerTargetsException(String message) {
        super(message);
    }

    public FboIncompleteLayerTargetsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FboIncompleteLayerTargetsException(Throwable cause) {
        super(cause);
    }

    public FboIncompleteLayerTargetsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
