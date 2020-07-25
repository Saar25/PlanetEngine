package org.saar.core.renderer.basic;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.node.Node;

public interface BasicNode extends Node {

    ModelVertices<? extends BasicVertex> getVertices();

    ModelIndices getIndices();

}
