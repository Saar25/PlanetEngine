package org.saar.core.common.primitive;

import org.saar.core.model.Vertex;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;

public class PrimitiveMesh implements Vertex {

    private final ModelVertices<PrimitiveVertex> vertices;
    private final ModelIndices indices;

    public PrimitiveMesh(ModelVertices<PrimitiveVertex> vertices) {
        this(vertices, new ModelIndices());
    }

    public PrimitiveMesh(ModelVertices<PrimitiveVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public ModelVertices<PrimitiveVertex> getVertices() {
        return this.vertices;
    }

    public ModelIndices getIndices() {
        return this.indices;
    }
}
