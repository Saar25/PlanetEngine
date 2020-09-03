package org.saar.lwjgl.opengl.objects;

public class Viewport {

    private final int x;
    private final int y;
    private final int w;
    private final int h;

    private Viewport(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public static Viewport crop(int x, int y, int w, int h) {
        return new Viewport(x, y, w, h);
    }

    public static Viewport size(int w, int h) {
        return new Viewport(0, 0, w, h);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.w;
    }

    public int getHeight() {
        return this.h;
    }
}
