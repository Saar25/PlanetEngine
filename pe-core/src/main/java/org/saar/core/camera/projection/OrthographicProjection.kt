package org.saar.core.camera.projection;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.camera.Projection;
import org.saar.maths.utils.Matrix4;

public abstract class OrthographicProjection implements Projection {

    private final Matrix4f matrix = Matrix4.create();

    public abstract float getLeft();

    public abstract float getRight();

    public abstract float getBottom();

    public abstract float getTop();

    public abstract float getzNear();

    public abstract float getzFar();

    @Override
    public final Matrix4fc getMatrix() {
        return Matrix4.ofProjection(getLeft(), getRight(), getBottom(),
                getTop(), getzNear(), getzFar(), this.matrix);
    }
}
