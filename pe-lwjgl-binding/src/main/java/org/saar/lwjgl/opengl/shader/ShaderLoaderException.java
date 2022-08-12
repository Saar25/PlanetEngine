package org.saar.lwjgl.opengl.shader;

public class ShaderLoaderException extends RuntimeException {

    public ShaderLoaderException() {
    }

    public ShaderLoaderException(String message) {
        super(message);
    }

    public ShaderLoaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShaderLoaderException(Throwable cause) {
        super(cause);
    }

    public ShaderLoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
