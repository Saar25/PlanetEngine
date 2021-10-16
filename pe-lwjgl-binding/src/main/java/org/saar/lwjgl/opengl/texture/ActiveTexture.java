package org.saar.lwjgl.opengl.texture;

public final class ActiveTexture {

    private static int active = 0;

    private ActiveTexture() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isActive(int unit) {
        return ActiveTexture.active == unit;
    }

    public static void set(int unit) {
        ActiveTexture.active = unit;
    }
}
