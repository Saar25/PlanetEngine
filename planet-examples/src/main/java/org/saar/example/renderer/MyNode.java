package org.saar.example.renderer;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.node.AbstractNode;
import org.saar.core.renderer.basic.BasicNode;

public class MyNode extends AbstractNode implements BasicNode {

    private final ModelVertices<MyVertex> vertices;
    private final ModelIndices indices;

    public MyNode(ModelVertices<MyVertex> vertices, ModelIndices indices) {
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
