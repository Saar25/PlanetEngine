package org.saar.core.renderer.r3d;

import org.saar.core.model.ModelNode;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;

public interface ModelNode3D extends ModelNode {

    ModelVertices<? extends Vertex3D> getVertices();

    ModelIndices getIndices();

}
