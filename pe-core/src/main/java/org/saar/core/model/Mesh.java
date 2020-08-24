package org.saar.core.model;

import org.saar.core.model.vertex.ModelVertex;
import org.saar.core.model.vertex.ModelVertices;

public interface Mesh {

    ModelVertices<? extends ModelVertex> getVertices();

}
