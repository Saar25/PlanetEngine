package org.saar.core.camera.projection;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.camera.Projection;
import org.saar.maths.utils.Matrix4;

public class PerspectiveProjection implements Projection {

    private float fov;
    private float width;
    private float height;
    private float near;
    private float far;

    private final Matrix4f matrix = Matrix4.create();

    public PerspectiveProjection(float fov, float width, float height, float near, float far) {
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.near = near;
        this.far = far;
    }

    public float getFov() {
        return this.fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getNear() {
        return this.near;
    }

    public void setNear(float near) {
        this.near = near;
    }

    public float getFar() {
        return this.far;
    }

    public void setFar(float far) {
        this.far = far;
    }

    @Override
    public Matrix4fc getMatrix() {
        return Matrix4.ofProjection(this.fov, this.width,
                this.height, this.near, this.far, this.matrix);
    }
}
