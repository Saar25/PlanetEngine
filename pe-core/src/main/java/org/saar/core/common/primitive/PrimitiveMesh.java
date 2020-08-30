package org.saar.core.common.primitive;

import org.saar.core.model.Vertex;
import org.saar.core.model.vertex.ModelIndices;

import java.util.List;

public class PrimitiveMesh implements Vertex {

    private final List<PrimitiveVertex> vertices;
    private final ModelIndices indices;

    public PrimitiveMesh(List<PrimitiveVertex> vertices) {
        this(vertices, new ModelIndices());
    }

    public PrimitiveMesh(List<PrimitiveVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public List<PrimitiveVertex> getVertices() {
        return this.vertices;
    }

    public ModelIndices getIndices() {
        return this.indices;
    }
}
