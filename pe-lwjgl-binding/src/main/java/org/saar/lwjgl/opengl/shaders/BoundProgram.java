package org.saar.lwjgl.opengl.shaders;

public final class BoundProgram {

    private static int bound = 0;

    private BoundProgram() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(int id) {
        return BoundProgram.get() == id;
    }

    public static void set(int id) {
        BoundProgram.bound = id;
    }

    public static int get() {
        return BoundProgram.bound;
    }

}
