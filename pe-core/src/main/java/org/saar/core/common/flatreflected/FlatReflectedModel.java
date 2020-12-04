package org.saar.core.common.flatreflected;

import org.joml.Vector3f;
import org.saar.core.mesh.Model;
import org.saar.maths.transform.SimpleTransform;

public class FlatReflectedModel implements Model {

    private final FlatReflectedMesh mesh;
    private final Vector3f normal;

    private final SimpleTransform transform = new SimpleTransform();

    public FlatReflectedModel(FlatReflectedMesh mesh, Vector3f normal) {
        this.mesh = mesh;
        this.normal = normal;
    }

    public Vector3f getNormal() {
        return this.normal;
    }

    public SimpleTransform getTransform() {
        return this.transform;
    }

    @Override
    public void draw() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}
