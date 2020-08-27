package org.saar.core.common.obj;

import org.saar.core.model.Mesh;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;

public class ObjMesh implements Mesh {

    private final ModelVertices<ObjVertex> vertices;
    private final ModelIndices indices;

    public ObjMesh(ModelVertices<ObjVertex> vertices, ModelIndices indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public ModelVertices<ObjVertex> getVertices() {
        return this.vertices;
    }

    public ModelIndices getIndices() {
        return this.indices;
    }
}
