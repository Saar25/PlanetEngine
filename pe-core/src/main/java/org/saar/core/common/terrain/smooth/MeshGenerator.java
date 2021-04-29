package org.saar.core.common.terrain.smooth;

import org.saar.core.common.smooth.SmoothVertex;
import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;

public abstract class MeshGenerator {

    public abstract int getTotalVertexCount();

    public abstract int getTotalIndexCount();

    public abstract void generateVertices(MeshVertexWriter<SmoothVertex> writer);

    public abstract void generateIndices(MeshIndexWriter writer);

}
