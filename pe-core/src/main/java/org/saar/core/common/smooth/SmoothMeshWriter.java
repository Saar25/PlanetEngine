package org.saar.core.common.smooth;

import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;

public class SmoothMeshWriter implements MeshVertexWriter<SmoothVertex>, MeshIndexWriter {

    private final SmoothMeshPrototype prototype;

    public SmoothMeshWriter(SmoothMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(SmoothVertex vertex) {
        this.prototype.getPositionBuffer().getWriter().write(vertex.getPosition3f());
        this.prototype.getNormalBuffer().getWriter().write(vertex.getNormal3f());
        this.prototype.getColourBuffer().getWriter().write(vertex.getColour3f());
        this.prototype.getTargetBuffer().getWriter().write(vertex.getTarget3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().write(index);
    }

}
