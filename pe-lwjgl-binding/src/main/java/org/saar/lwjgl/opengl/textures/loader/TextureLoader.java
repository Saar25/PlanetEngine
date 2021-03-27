package org.saar.lwjgl.opengl.textures.loader;

import org.saar.lwjgl.opengl.textures.TextureInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class TextureLoader {

    private static final Map<String, TextureFileLoader> loaders = new HashMap<>();

    static {
        loaders.put("png", new PNGFileLoader());
    }

    private TextureLoader() {

    }

    private static UnsupportedFileTypeException typeException(String type) {
        return new UnsupportedFileTypeException("File type not supported" + type);
    }

    /**
     * Loads a texture file into width, height and data
     * and return the values as a {@link TextureInfo} object
     *
     * @param file the texture file path
     * @return the texture info
     * @throws IOException                  if an error occurred during the file loading
     * @throws UnsupportedFileTypeException if the texture type is not recognized
     */
    public static TextureInfo load(String file) throws IOException, UnsupportedFileTypeException {
        final String type = file.substring(file.lastIndexOf(".") + 1);
        final Optional<TextureFileLoader> loader = Optional.ofNullable(loaders.get(type));
        return loader.orElseThrow(() -> typeException(type)).load(file);
    }
}
