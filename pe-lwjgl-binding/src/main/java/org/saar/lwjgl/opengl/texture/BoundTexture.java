package org.saar.lwjgl.opengl.texture;

import java.util.EnumMap;
import java.util.Map;

public final class BoundTexture {

    private static final Map<TextureTarget, Integer> bound = new EnumMap<>(TextureTarget.class);

    private BoundTexture() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(TextureTarget target, int id) {
        return BoundTexture.get(target) == id;
    }

    public static void set(TextureTarget target, int id) {
        BoundTexture.bound.put(target, id);
    }

    public static int get(TextureTarget target) {
        return BoundTexture.bound.getOrDefault(target, 0);
    }

}
