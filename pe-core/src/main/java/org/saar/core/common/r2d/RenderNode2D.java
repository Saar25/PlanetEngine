package org.saar.core.common.r2d;

import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;

public class RenderNode2D extends AbstractNode implements RenderNode, Node2D {

    private final Mesh2D mesh;

    public RenderNode2D(Mesh2D mesh) {
        this.mesh = mesh;
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
