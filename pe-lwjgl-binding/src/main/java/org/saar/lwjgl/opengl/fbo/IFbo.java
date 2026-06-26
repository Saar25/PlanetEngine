package org.saar.lwjgl.opengl.fbo;

public interface IFbo extends ReadOnlyFbo, ModifiableFbo {

    /**
     * Delete the fbo
     */
    void delete();

}
