package org.saar.core.camera.projection;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.camera.Projection;
import org.saar.maths.utils.Matrix4;

public class OrthographicProjection implements Projection {

    private final float left;
    private final float right;
    private final float bottom;
    private final float top;
    private final float zNear;
    private final float zFar;

    private final Matrix4f matrix = Matrix4.create();

    public OrthographicProjection(float left, float right, float bottom, float top, float zNear, float zFar) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.zNear = zNear;
        this.zFar = zFar;
    }

    @Override
    public Matrix4fc getMatrix() {
        return Matrix4.ofProjection(this.left, this.right, this.bottom,
                this.top, this.zNear, this.zFar, this.matrix);
    }
}
