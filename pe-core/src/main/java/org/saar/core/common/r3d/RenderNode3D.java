package org.saar.core.common.r3d;

import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.maths.objects.Transform;

public class RenderNode3D extends AbstractNode implements RenderNode {

    private final Mesh3D mesh;

    private final Transform transform = new Transform();

    public RenderNode3D(Mesh3D mesh) {
        this.mesh = mesh;
    }

    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public void render() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}
