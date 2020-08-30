package org.saar.core.renderer.r2d;

import org.saar.core.model.Model;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;

public class RenderNode2D extends AbstractNode implements RenderNode {

    private final ModelBuffers2D buffers;

    public RenderNode2D(Vertex2D[] vertices, int[] indices) {
        this.buffers = new ModelBuffers2DOneVbo(vertices.length, indices.length);
        this.buffers.load(vertices, indices);
    }

    @Override
    public Model getModel() {
        return this.buffers.getModel();
    }
}
