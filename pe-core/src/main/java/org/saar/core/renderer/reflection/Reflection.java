package org.saar.core.renderer.reflection;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.core.camera.Camera;
import org.saar.core.renderer.RenderingPath;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.maths.objects.Planef;
import org.saar.maths.transform.Rotation;
import org.saar.maths.utils.Vector3;

public class Reflection {

    private final Planef plane;
    private final Camera camera;
    private final Camera reflectionCamera;
    private final RenderingPath renderingPath;

    private ReadOnlyTexture reflectionMap = Texture.NULL;

    public Reflection(Planef plane, Camera camera, Camera reflectionCamera, RenderingPath renderingPath) {
        this.plane = plane;
        this.camera = camera;
        this.reflectionCamera = reflectionCamera;
        this.renderingPath = renderingPath;
    }

    private void reflect() {
        final Vector3fc normal = Vector3.normalize(this.plane.a, this.plane.b, this.plane.c);

        final Vector3fc p = this.camera.getTransform().getPosition().getValue();
        final float distance = getPlane().distance(p.x(), p.y(), p.z());
        final Vector3f ptc = Vector3.mul(normal, distance * 2);
        this.reflectionCamera.getTransform().getPosition().set(p.sub(ptc, ptc));

        final Rotation rotation = this.camera.getTransform().getRotation();
        final Vector3fc reflect = rotation.getDirection().reflect(normal).negate();
        this.reflectionCamera.getTransform().getRotation().lookAlong(reflect);
    }

    public void updateReflectionMap() {
        reflect();

        this.reflectionMap = this.renderingPath.render().toTexture();
    }

    public ReadOnlyTexture getReflectionMap() {
        return this.reflectionMap;
    }

    public Planef getPlane() {
        return this.plane;
    }

    public void delete() {
        this.renderingPath.delete();
    }
}
