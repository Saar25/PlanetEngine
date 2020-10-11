package org.saar.core.common.flatreflected;

import org.saar.core.node.Model;
import org.saar.maths.transform.SimpleTransform;

public class FlatReflectedModel implements Model {

    private final FlatReflectedMesh mesh;

    private final SimpleTransform transform = new SimpleTransform();

    public FlatReflectedModel(FlatReflectedMesh mesh) {
        this.mesh = mesh;
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
