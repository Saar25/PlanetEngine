package org.saar.core.renderer.r3d;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.node.Node;
import org.saar.maths.objects.Transform;

public interface Node3D extends Node {

    ModelVertices<? extends Vertex3D> getVertices();

    ModelIndices getIndices();

    Transform getTransform();

}
