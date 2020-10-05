package org.saar.core.renderer.reflection;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.Projection;
import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.transform.Transform;
import org.saar.maths.utils.Matrix4;

public class ReflectionCamera implements ICamera {

    private final Matrix4f viewMatrix = Matrix4.create();
    private final Transform transform = new SimpleTransform();

    @Override
    public Matrix4fc getViewMatrix() {
        return Matrix4.ofView(getTransform().getPosition().getValue(),
                getTransform().getRotation().getValue(), this.viewMatrix);
    }

    @Override
    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public Projection getProjection() {
        return null;
    }
}
