package org.saar.core.renderer.r3d;

import org.saar.core.model.Model;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;

public class RenderNode3D extends AbstractNode implements RenderNode {

    private final ModelBuffers3D buffers;

    public RenderNode3D(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        this.buffers = new ModelBuffers3DOneVbo(vertices.length, indices.length, instances.length);
        this.buffers.load(vertices, indices, instances);
    }

    @Override
    public Model getModel() {
        return this.buffers.getModel();
    }
}
