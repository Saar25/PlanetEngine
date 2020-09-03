package org.saar.lwjgl.opengl.objects.rbos;

final class BoundRbo {

    private static int bound = 0;

    private BoundRbo() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(int id) {
        return BoundRbo.get() == id;
    }

    public static void set(int id) {
        BoundRbo.bound = id;
    }

    public static int get() {
        return BoundRbo.bound;
    }

}
