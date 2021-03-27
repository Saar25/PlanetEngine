package org.saar.core.camera.projection;

import org.saar.core.camera.Projection;

public class SimplePerspectiveProjection extends PerspectiveProjection implements Projection {

    private float fov;
    private float width;
    private float height;
    private float near;
    private float far;

    public SimplePerspectiveProjection(float fov, float width, float height, float near, float far) {
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.near = near;
        this.far = far;
    }

    @Override
    public float getFov() {
        return this.fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public float getNear() {
        return this.near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    @Override
    public float getFar() {
        return this.far;
    }

    public void setFar(float far) {
        this.far = far;
    }
}
