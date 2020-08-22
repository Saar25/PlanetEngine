package org.saar.core.model;

import org.saar.core.model.vertex.ModelVertex;
import org.saar.core.model.vertex.ModelVertices;

public interface ModelNode {

    ModelVertices<? extends ModelVertex> getVertices();

}
