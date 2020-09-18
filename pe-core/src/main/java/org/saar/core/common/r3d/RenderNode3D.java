package org.saar.core.common.r3d;

import org.saar.core.node.AbstractNode;
import org.saar.core.node.RenderNode;
import org.saar.maths.objects.Transform;

public class RenderNode3D extends AbstractNode implements RenderNode {

    private final ModelBuffers3D buffers;

    private final Transform transform = new Transform();

    public RenderNode3D(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        this.buffers = new ModelBuffers3DOneVbo(vertices.length, indices.length, instances.length);
        this.buffers.load(vertices, indices, instances);
    }

    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public void render() {
        this.buffers.getMesh().draw();
    }

    @Override
    public void delete() {
        this.buffers.getMesh().delete();
    }
}
