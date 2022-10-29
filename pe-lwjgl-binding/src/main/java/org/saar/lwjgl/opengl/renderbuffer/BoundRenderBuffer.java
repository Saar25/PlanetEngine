package org.saar.lwjgl.opengl.renderbuffer;

final class BoundRenderBuffer {

    private static int bound = 0;

    private BoundRenderBuffer() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static boolean isBound(int id) {
        return BoundRenderBuffer.get() == id;
    }

    public static void set(int id) {
        BoundRenderBuffer.bound = id;
    }

    public static int get() {
        return BoundRenderBuffer.bound;
    }

}
