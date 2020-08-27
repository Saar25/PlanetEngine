package org.saar.core.renderer.r2d;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;

public interface Mesh2D extends Mesh {

    ModelVertices<? extends Vertex2D> getVertices();

    ModelIndices getIndices();

}
