package org.saar.core.common.obj;

import org.saar.core.mesh.writers.ElementsMeshWriter;

public class ObjMeshWriter implements ElementsMeshWriter<ObjVertex> {

    private final ObjMeshPrototype prototype;

    public ObjMeshWriter(ObjMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(ObjVertex vertex) {
        this.prototype.getPositionBuffer().getWriter().write3f(vertex.getPosition3f());
        this.prototype.getUvCoordBuffer().getWriter().write2f(vertex.getUvCoord2f());
        this.prototype.getNormalBuffer().getWriter().write3f(vertex.getNormal3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().writeInt(index);
    }

}
