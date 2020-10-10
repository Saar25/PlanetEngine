package org.saar.core.common.flatreflected;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelVertexWriter;

public class FlatReflectedMeshWriter implements ModelVertexWriter<FlatReflectedVertex>, ModelIndexWriter {

    private final FlatReflectedMeshPrototype prototype;

    public FlatReflectedMeshWriter(FlatReflectedMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(FlatReflectedVertex vertex) {
        this.prototype.getPositionBuffer().getWriter().write(vertex.getPosition3f());
        this.prototype.getNormalBuffer().getWriter().write(vertex.getNormal3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().write(index);
    }

}
