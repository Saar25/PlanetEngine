package org.saar.core.renderer.reflection;

import org.joml.Planef;
import org.joml.Vector3fc;
import org.saar.core.camera.Camera;
import org.saar.core.camera.ICamera;
import org.saar.core.renderer.RenderingPath;
import org.saar.maths.transform.Position;
import org.saar.maths.transform.Rotation;
import org.saar.maths.utils.Vector3;

public class Reflection {

    private final Planef plane;
    private final Camera camera;
    private final Camera reflectionCamera;
    private final RenderingPath renderingPath;

    public Reflection(Planef plane, Camera camera, Camera reflectionCamera, RenderingPath renderingPath) {
        this.plane = plane;
        this.camera = camera;
        this.reflectionCamera = reflectionCamera;
        this.renderingPath = renderingPath;
    }

    private void reflect() {
        final Vector3fc normal = Vector3.normalize(this.plane.a, this.plane.b, this.plane.c);

        final Position p = this.camera.getTransform().getPosition();
        final float distance = getPlane().distance(p.getX(), p.getY(), p.getZ());
        final Vector3fc ptc = Vector3.mul(normal, distance * 2);
        this.reflectionCamera.getTransform().getPosition().sub(ptc);

        final Rotation rotation = this.camera.getTransform().getRotation();
        final Vector3fc reflect = rotation.getDirection().reflect(normal);
        this.reflectionCamera.getTransform().getRotation().lookAlong(reflect);
    }

    public void updateReflectionMap() {
        reflect();

        this.renderingPath.render();
    }

    public Planef getPlane() {
        return this.plane;
    }

    public void delete() {
        this.renderingPath.delete();
    }
}
