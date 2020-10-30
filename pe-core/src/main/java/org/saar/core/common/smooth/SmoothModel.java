package org.saar.core.common.smooth;

import org.saar.core.node.Model;
import org.saar.maths.transform.SimpleTransform;
import org.saar.maths.transform.Transform;

public class SmoothModel implements Model {

    private final Transform transform = new SimpleTransform();
    private final SmoothMesh mesh;

    public SmoothModel(SmoothMesh mesh) {
        this.mesh = mesh;
    }

    public Transform getTransform() {
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
