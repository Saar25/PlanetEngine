package org.saar.core.common.r3d;

import org.saar.core.node.RenderNode;
import org.saar.maths.transform.SimpleTransform;

public class RenderNode3D implements Node3D, RenderNode {

    private final Mesh3D mesh;

    private final SimpleTransform transform = new SimpleTransform();

    public RenderNode3D(Mesh3D mesh) {
        this.mesh = mesh;
    }

    @Override
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
