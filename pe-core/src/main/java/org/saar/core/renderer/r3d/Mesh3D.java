package org.saar.core.renderer.r3d;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;

public interface Mesh3D extends Mesh {

    ModelVertices<? extends Vertex3D> getVertices();

    ModelIndices getIndices();

}
