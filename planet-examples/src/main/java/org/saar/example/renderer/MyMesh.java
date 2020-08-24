package org.saar.example.renderer;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.core.renderer.basic.BasicMesh;

public class MyMesh implements BasicMesh {

    private final ModelVertices<MyVertex> vertices;
    private final ModelIndices indices;

    public MyMesh(ModelVertices<MyVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    @Override
    public ModelVertices<MyVertex> getVertices() {
        return this.vertices;
    }

    @Override
    public ModelIndices getIndices() {
        return this.indices;
    }
}
