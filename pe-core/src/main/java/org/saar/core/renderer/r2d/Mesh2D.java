package org.saar.core.renderer.r2d;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;

import java.util.List;

public interface Mesh2D extends Mesh {

    List<? extends Vertex2D> getVertices();

    ModelIndices getIndices();

}
