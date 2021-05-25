package org.saar.lwjgl.opengl.objects.vaos;

import org.saar.lwjgl.opengl.objects.attributes.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.ReadOnlyVbo;

public interface WritableVao extends ReadOnlyVao {

    /**
     * Loads a vbo and linking the given attributes to it
     *
     * @param buffer     the vbo to load
     * @param attributes the attributes
     */
    void loadVbo(ReadOnlyVbo buffer, Attribute... attributes);

}
