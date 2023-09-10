package org.saar.core.renderer.shadow;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.camera.ICamera;
import org.saar.core.camera.projection.OrthographicProjection;
import org.saar.core.light.IDirectionalLight;
import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.utils.Matrix4;
import org.saar.maths.utils.Vector3;

public class ShadowsCamera implements ICamera {

    private final Matrix4f viewMatrix = Matrix4.create();
    private final SimpleTransform transform = new SimpleTransform();

    private final OrthographicProjection projection;
    private final IDirectionalLight light;

    public ShadowsCamera(OrthographicProjection projection, IDirectionalLight light) {
        this.projection = projection;
        this.light = light;
    }

    @Override
    public Matrix4fc getViewMatrix() {
        return Matrix4.ofView(
                getTransform().getPosition().getValue(),
                getTransform().getRotation().getValue(),
                this.viewMatrix
        ).lookAlong(this.light.getDirection(), Vector3.UP);
    }

    @Override
    public SimpleTransform getTransform() {
        return this.transform;
    }

    @Override
    public OrthographicProjection getProjection() {
        return this.projection;
    }
}
