package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.texture.parameter.TextureParameter;

public interface ReadOnlyTexture {

    void applyParameters(TextureParameter[] parameters);

    void generateMipmap();

    void bind(int unit);

    void bind();

    void unbind();

    void delete();

}
