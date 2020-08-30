package org.saar.lwjgl.opengl.objects.vaos;

final class BoundVao {

    private static int bound = 0;

    private BoundVao() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(int id) {
        return BoundVao.get() == id;
    }

    public static void set(int id) {
        BoundVao.bound = id;
    }

    public static int get() {
        return BoundVao.bound;
    }

}
