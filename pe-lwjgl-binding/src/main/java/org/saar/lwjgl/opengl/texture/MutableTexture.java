package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.texture.parameter.TextureParameter;

public interface MutableTexture extends ReadOnlyTexture {

    void applyParameters(TextureParameter[] parameters);

    void generateMipmap();

}
