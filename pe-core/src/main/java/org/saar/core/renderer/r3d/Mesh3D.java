package org.saar.core.renderer.r3d;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;

import java.util.List;

public interface Mesh3D extends Mesh {

    List<? extends Vertex3D> getVertices();

    ModelIndices getIndices();

}
