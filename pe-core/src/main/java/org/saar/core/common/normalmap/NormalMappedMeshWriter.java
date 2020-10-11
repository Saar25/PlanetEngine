package org.saar.core.common.normalmap;

import org.saar.core.model.mesh.writers.MeshIndexWriter;
import org.saar.core.model.mesh.writers.MeshVertexWriter;

public class NormalMappedMeshWriter implements MeshVertexWriter<NormalMappedVertex>, MeshIndexWriter {

    private final NormalMappedMeshPrototype prototype;

    public NormalMappedMeshWriter(NormalMappedMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(NormalMappedVertex vertex) {
        this.prototype.getPositionBuffer().getWriter().write(vertex.getPosition3f());
        this.prototype.getUvCoordBuffer().getWriter().write(vertex.getUvCoord2f());
        this.prototype.getNormalBuffer().getWriter().write(vertex.getNormal3f());
        this.prototype.getTangentBuffer().getWriter().write(vertex.getTangent3f());
        this.prototype.getBiTangentBuffer().getWriter().write(vertex.getBiTangent3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().write(index);
    }

}
