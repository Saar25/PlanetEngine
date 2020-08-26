package org.saar.core.model;

import org.saar.core.model.vertex.ModelVertices;

public interface Mesh {

    ModelVertices<? extends Vertex> getVertices();

}
