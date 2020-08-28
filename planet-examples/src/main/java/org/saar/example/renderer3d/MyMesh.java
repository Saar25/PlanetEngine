package org.saar.example.renderer3d;

import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.renderer.r3d.Mesh3D;

import java.util.List;

public class MyMesh implements Mesh3D {

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
