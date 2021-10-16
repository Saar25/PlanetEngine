package org.saar.lwjgl.opengl.texture;

import java.util.HashMap;
import java.util.Map;

public final class TextureCache {

    private static final Map<String, ReadOnlyTexture> CACHE = new HashMap<>();

    private TextureCache() {

    }

    public static void addToCache(String file, ReadOnlyTexture texture) {
        CACHE.put(file, texture);
    }

    public static ReadOnlyTexture getTexture(String file) {
        return CACHE.get(file);
    }
}
