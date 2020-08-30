package org.saar.lwjgl.opengl.objects.vaos;

import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;

public interface IVao extends ReadOnlyVao {

    /**
     * Enables the vao attributes
     */
    void enableAttributes();

    /**
     * Disables the vao attributes
     */
    void disableAttributes();

    /**
     * Loads a vbo and linking the given attributes to it
     *
     * @param vbo        the vbo to load
     * @param attributes the attributes
     */
    void loadVbo(IVbo vbo, Attribute... attributes);

    /**
     * Delete this vao and its related buffers
     */
    void delete();

}
