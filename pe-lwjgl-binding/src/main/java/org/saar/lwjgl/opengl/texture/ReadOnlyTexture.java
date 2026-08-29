package org.saar.lwjgl.opengl.texture;

public interface ReadOnlyTexture {

    void bind(int unit);

    void bind();

    void delete();

}
