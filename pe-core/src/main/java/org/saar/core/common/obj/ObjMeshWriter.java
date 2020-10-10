package org.saar.core.common.obj;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelVertexWriter;

public class ObjMeshWriter implements ModelVertexWriter<ObjVertex>, ModelIndexWriter {

    private final ObjMeshPrototype prototype;

    public ObjMeshWriter(ObjMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(ObjVertex vertex) {
        this.prototype.getPositionBuffer().getWriter().write(vertex.getPosition3f());
        this.prototype.getUvCoordBuffer().getWriter().write(vertex.getUvCoord2f());
        this.prototype.getNormalBuffer().getWriter().write(vertex.getNormal3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().write(index);
    }

}
