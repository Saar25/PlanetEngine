package org.saar.core.common.r3d;

import org.saar.core.mesh.Model;
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
    public Mesh3D getMesh() {
        return this.mesh;
    }
}
