package org.saar.core.common.r2d;

import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;

public class Mesh2DWriter implements MeshVertexWriter<Vertex2D>, MeshIndexWriter {

    private final Mesh2DPrototype prototype;

    public Mesh2DWriter(Mesh2DPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(Vertex2D vertex) {
        this.prototype.getPositionBuffer().getWriter().write(vertex.getPosition2f());
        this.prototype.getColourBuffer().getWriter().write(vertex.getColour3f());
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().write(index);
    }

}
