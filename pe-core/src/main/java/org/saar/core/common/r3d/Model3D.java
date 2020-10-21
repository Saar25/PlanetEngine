package org.saar.core.common.r3d;

import org.saar.core.node.Model;
import org.saar.maths.transform.SimpleTransform;

public class Model3D implements Model {

    private final Mesh3D mesh;

    private final SimpleTransform transform = new SimpleTransform();

    public Model3D(Mesh3D mesh) {
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
