package org.saar.lwjgl.opengl.vaos;

import org.saar.lwjgl.opengl.objects.attributes.IAttribute;
import org.saar.lwjgl.opengl.objects.vbos.ReadOnlyVbo;

public interface WritableVao extends ReadOnlyVao {

    /**
     * Loads a vbo and linking the given attributes to it
     *
     * @param buffer     the vbo to load
     * @param attributes the attributes
     */
    void loadVbo(ReadOnlyVbo buffer, IAttribute... attributes);

}
