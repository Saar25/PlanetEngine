package org.saar.core.common.r2d;

import org.saar.core.model.loader.ModelIndexWriter;
import org.saar.core.model.loader.ModelVertexWriter;

public class Mesh2DWriter implements ModelVertexWriter<Vertex2D>, ModelIndexWriter {

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
