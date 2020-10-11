package org.saar.lwjgl.opengl.textures;

public final class ActiveTexture {

    private static final int MAX_TEXTURE_UNITS = 32;

    private static int active = 0;

    private ActiveTexture() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isActive(int unit) {
        return ActiveTexture.get() == unit;
    }

    public static void set(int unit) {
        ActiveTexture.active = unit;
    }

    public static int get() {
        return ActiveTexture.active;
    }

}
