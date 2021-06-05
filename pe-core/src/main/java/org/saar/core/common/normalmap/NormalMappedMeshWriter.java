package org.saar.core.common.normalmap;

import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;

public class NormalMappedMeshWriter implements MeshVertexWriter<NormalMappedVertex>, MeshIndexWriter {

    private final NormalMappedMeshPrototype prototype;

    public NormalMappedMeshWriter(NormalMappedMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(NormalMappedVertex vertex) {
        this.prototype.getPositionBuffer().getWriter().write3f(vertex.getPosition3f());
        this.prototype.getUvCoordBuffer().getWriter().write2f(vertex.getUvCoord2f());
        this.prototype.getNormalBuffer().getWriter().write3f(vertex.getNormal3f());
        this.prototype.getTangentBuffer().getWriter().write3f(vertex.getTangent3f());
        this.prototype.getBiTangentBuffer().getWriter().write3f(vertex.getBiTangent3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().writeInt(index);
    }

}
