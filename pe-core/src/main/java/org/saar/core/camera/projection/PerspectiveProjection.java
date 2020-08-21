package org.saar.core.camera.projection;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.camera.Projection;
import org.saar.maths.utils.Matrix4;

public class PerspectiveProjection implements Projection {

    private final float fov;
    private final float width;
    private final float height;
    private final float zNear;
    private final float zFar;

    private final Matrix4f matrix = Matrix4.create();

    public PerspectiveProjection(float fov, float width, float height, float zNear, float zFar) {
        this.fov = fov;
        this.width = width;
        this.height = height;
        this.zNear = zNear;
        this.zFar = zFar;
    }

    @Override
    public Matrix4fc getMatrix() {
        return Matrix4.ofProjection(this.fov, this.width,
                this.height, this.zNear, this.zFar, this.matrix);
    }
}
