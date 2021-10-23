package org.saar.core.common.r2d;

import org.saar.core.mesh.writers.ElementsMeshWriter;

public class MeshWriter2D implements ElementsMeshWriter<Vertex2D> {

    private final MeshPrototype2D prototype;

    public MeshWriter2D(MeshPrototype2D prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(Vertex2D vertex) {
        this.prototype.getPositionBuffer().getWriter().write2f(vertex.getPosition2f());
        this.prototype.getColourBuffer().getWriter().write3f(vertex.getColour3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().writeInt(index);
    }
}
