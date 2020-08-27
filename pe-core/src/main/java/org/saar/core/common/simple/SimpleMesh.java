package org.saar.core.common.simple;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;

public class SimpleMesh implements Mesh {

    private final ModelVertices<SimpleVertex> vertices;
    private final ModelIndices indices;

    public SimpleMesh(ModelVertices<SimpleVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public ModelVertices<SimpleVertex> getVertices() {
        return this.vertices;
    }

    public ModelIndices getIndices() {
        return this.indices;
    }
}
