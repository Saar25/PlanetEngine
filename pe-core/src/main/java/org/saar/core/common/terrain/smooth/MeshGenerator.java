package org.saar.core.common.terrain.smooth;

import org.saar.core.common.smooth.SmoothMeshWriter;

public abstract class MeshGenerator {

    public abstract int getTotalVertexCount();

    public abstract int getTotalIndexCount();

    public abstract void generateVertices(SmoothMeshWriter writer);

    public abstract void generateIndices(SmoothMeshWriter writer);

}
