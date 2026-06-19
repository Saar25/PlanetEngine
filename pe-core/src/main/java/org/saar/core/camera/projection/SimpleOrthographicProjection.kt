package org.saar.core.camera.projection;

import org.saar.core.camera.Projection;

public class SimpleOrthographicProjection extends OrthographicProjection implements Projection {

    private final float left;
    private final float right;
    private final float bottom;
    private final float top;
    private final float zNear;
    private final float zFar;

    public SimpleOrthographicProjection(float left, float right, float bottom, float top, float zNear, float zFar) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.zNear = zNear;
        this.zFar = zFar;
    }

    @Override
    public float getLeft() {
        return this.left;
    }

    @Override
    public float getRight() {
        return this.right;
    }

    @Override
    public float getBottom() {
        return this.bottom;
    }

    @Override
    public float getTop() {
        return this.top;
    }

    @Override
    public float getzNear() {
        return this.zNear;
    }

    @Override
    public float getzFar() {
        return this.zFar;
    }
}
