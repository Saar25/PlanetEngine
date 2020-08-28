package org.saar.core.common.simple;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;

import java.util.List;

public class SimpleMesh implements Mesh {

    private final List<SimpleVertex> vertices;
    private final ModelIndices indices;

    public SimpleMesh(List<SimpleVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public List<SimpleVertex> getVertices() {
        return this.vertices;
    }

    public ModelIndices getIndices() {
        return this.indices;
    }
}
