package org.saar.lwjgl.assimp;

public class AssimpException extends RuntimeException {

    public AssimpException() {
    }

    public AssimpException(String message) {
        super(message);
    }

    public AssimpException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssimpException(Throwable cause) {
        super(cause);
    }

    public AssimpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
