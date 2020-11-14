package org.saar.lwjgl.opengl.textures.loader;

import org.saar.lwjgl.opengl.textures.TextureInfo;

import java.io.IOException;

public interface TextureFileLoader {

    TextureInfo load(String file) throws IOException;

}
