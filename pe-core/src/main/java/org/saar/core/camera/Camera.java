package org.saar.core.camera;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.maths.transform.Transform;
import org.saar.maths.utils.Matrix4;

public class Camera implements ICamera {

    private final Matrix4f viewMatrix = Matrix4.create();
    private final Transform transform = new Transform();
    private final Projection projection;

    public Camera(Projection projection) {
        this.projection = projection;
    }

    @Override
    public Matrix4fc getViewMatrix() {
        return Matrix4.ofView(
                getTransform().getPosition().getValue(),
                getTransform().getRotation().getValue(),
                this.viewMatrix);
    }

    @Override
    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public Projection getProjection() {
        return this.projection;
    }
}
