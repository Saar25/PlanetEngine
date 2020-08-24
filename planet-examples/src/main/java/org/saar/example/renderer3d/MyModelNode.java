package org.saar.example.renderer3d;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.node.AbstractNode;
import org.saar.core.renderer.r3d.ModelNode3D;

public class MyModelNode extends AbstractNode implements ModelNode3D {

    private final ModelVertices<MyVertex> vertices;
    private final ModelIndices indices;

    public MyModelNode(ModelVertices<MyVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    @Override
    public ModelVertices<MyVertex> getVertices() {
        return this.vertices;
    }

    @Override
    public ModelIndices getIndices() {
        return this.indices;
    }
}
