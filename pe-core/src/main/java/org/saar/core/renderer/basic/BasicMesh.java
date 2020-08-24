package org.saar.core.renderer.basic;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;

public interface BasicMesh extends Mesh {

    ModelVertices<? extends BasicVertex> getVertices();

    ModelIndices getIndices();

}
