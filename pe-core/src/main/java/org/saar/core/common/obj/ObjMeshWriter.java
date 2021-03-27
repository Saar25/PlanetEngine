package org.saar.core.common.obj;

import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;

public class ObjMeshWriter implements MeshVertexWriter<ObjVertex>, MeshIndexWriter {

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
