package org.saar.lwjgl.opengl.fbo;

public interface DrawableFbo extends ReadOnlyFbo {

    /**
     * Set as read fbo
     */
    void bindAsDraw();
}
