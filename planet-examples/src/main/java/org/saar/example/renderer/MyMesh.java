package org.saar.example.renderer;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.renderer.r2d.Mesh2D;

import java.util.List;

public class MyMesh implements Mesh2D {

    private final List<MyVertex> vertices;
    private final ModelIndices indices;

    public MyMesh(List<MyVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    @Override
    public List<MyVertex> getVertices() {
        return this.vertices;
    }

    @Override
    public ModelIndices getIndices() {
        return this.indices;
    }
}
