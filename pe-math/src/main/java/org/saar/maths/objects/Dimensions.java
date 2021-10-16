package org.saar.maths.objects;

public class Dimensions {

    private final int w;
    private final int h;

    public Dimensions(int width, int height) {
        this.w = width;
        this.h = height;
    }

    public int getWidth() {
        return this.w;
    }

    public int getHeight() {
        return this.h;
    }

    @Override
    public String toString() {
        return String.format("[Dimensions: w=%d, h=%d]", w, h);
    }
}
