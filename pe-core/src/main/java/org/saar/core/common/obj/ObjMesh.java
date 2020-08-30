package org.saar.core.common.obj;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;

import java.util.List;

public class ObjMesh implements Mesh {

    private final List<ObjVertex> vertices;
    private final ModelIndices indices;

    public ObjMesh(List<ObjVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public List<ObjVertex> getVertices() {
        return this.vertices;
    }

    public ModelIndices getIndices() {
        return this.indices;
    }
}
