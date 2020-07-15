package org.saar.core.renderer.basic;

import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.node.AbstractNode;
import org.saar.core.node.Node;

public class BasicNode extends AbstractNode implements Node {

    private final ModelVertices<BasicVertex> vertices;

    public BasicNode(ModelVertices<BasicVertex> vertices) {
        this.vertices = vertices;
    }

    public ModelVertices<BasicVertex> getVertices() {
        return this.vertices;
    }
}
