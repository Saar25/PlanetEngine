package org.saar.example.renderer3d;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.node.AbstractNode;
import org.saar.core.renderer.r3d.Node3D;
import org.saar.maths.objects.Transform;

public class MyNode extends AbstractNode implements Node3D {

    private final ModelVertices<MyVertex> vertices;
    private final ModelIndices indices;
    private final Transform transform;

    public MyNode(ModelVertices<MyVertex> vertices, ModelIndices indices, Transform transform) {
        this.vertices = vertices;
        this.indices = indices;
        this.transform = transform;
    }

    @Override
    public ModelVertices<MyVertex> getVertices() {
        return this.vertices;
    }

    @Override
    public ModelIndices getIndices() {
        return this.indices;
    }

    @Override
    public Transform getTransform() {
        return this.transform;
    }
}
