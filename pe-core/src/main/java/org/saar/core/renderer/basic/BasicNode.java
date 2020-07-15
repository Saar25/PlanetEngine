package org.saar.core.renderer.basic;

import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.Node;

public class BasicNode extends AbstractNode implements Node {

    private final ModelVertices<BasicModelVertex> vertices;

    public BasicNode(ModelVertices<BasicModelVertex> vertices) {
        this.vertices = vertices;
    }

    public ModelVertices<BasicModelVertex> getVertices() {
        return this.vertices;
    }
}
