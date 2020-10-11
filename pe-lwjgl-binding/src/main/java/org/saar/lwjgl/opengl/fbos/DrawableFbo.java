package org.saar.lwjgl.opengl.fbos;

public interface DrawableFbo extends ReadOnlyFbo {

    /**
     * Set as read fbo
     */
    void bindAsDraw();
}
