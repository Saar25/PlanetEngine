package org.saar.core.common.smooth;

import org.saar.core.node.Model;
import org.saar.maths.transform.Transform;

public class SmoothModel implements Model {

    private final SmoothNode node;
    private final SmoothMesh mesh;

    public SmoothModel(SmoothNode node, SmoothMesh mesh) {
        this.node = node;
        this.mesh = mesh;
    }

    public Transform getTransform() {
        return this.node.getTransform();
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
