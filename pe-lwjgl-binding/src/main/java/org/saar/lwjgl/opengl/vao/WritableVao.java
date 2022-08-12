package org.saar.lwjgl.opengl.vao;

import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.opengl.vbo.ReadOnlyVbo;

public interface WritableVao extends ReadOnlyVao {

    /**
     * Loads a vbo and linking the given attributes to it
     *
     * @param buffer     the vbo to load
     * @param attributes the attributes
     */
    void loadVbo(ReadOnlyVbo buffer, IAttribute... attributes);

}
