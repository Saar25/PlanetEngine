package org.saar.core.renderer.r2d;

import org.saar.core.model.DataWriter;

import java.util.List;

public class NodeWriter2D {

    private final DataWriter dataWriter = new DataWriter();
    private final DataWriter indexWriter = new DataWriter();

    private int vertices = 0;
    private int indexOffset = 0;

    private void writeVertex(Vertex2D vertex) {
        getDataWriter().write(vertex.getPosition2f());
        getDataWriter().write(vertex.getColour3f());
        this.vertices++;
    }

    private void writeIndex(int index) {
        getIndexWriter().write(this.indexOffset + index);
    }

    private void writeInstance(Node2D instance) {

    }

    public void write(Mesh2D mesh, Node2D node) {
        this.writeInstance(node);
        mesh.getVertices().forEach(this::writeVertex);
        mesh.getIndices().getIndices().forEach(this::writeIndex);
        this.indexOffset = this.vertices;
    }

    public void write(Mesh2D mesh, List<Node2D> nodes) {
        for (Node2D node : nodes) {
            this.write(mesh, node);
        }
    }

    public DataWriter getDataWriter() {
        return this.dataWriter;
    }

    public DataWriter getIndexWriter() {
        return this.indexWriter;
    }
}
