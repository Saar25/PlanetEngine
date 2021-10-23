package org.saar.core.common.flatreflected;

import org.saar.core.mesh.writers.ElementsMeshWriter;

public class FlatReflectedMeshWriter implements ElementsMeshWriter<FlatReflectedVertex> {

    private final FlatReflectedMeshPrototype prototype;

    public FlatReflectedMeshWriter(FlatReflectedMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(FlatReflectedVertex vertex) {
        this.prototype.getPositionBuffer().getWriter().write3f(vertex.getPosition3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().writeInt(index);
    }
}
