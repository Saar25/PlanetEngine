package org.saar.core.renderer.r3d;

import org.saar.core.model.data.DataWriter;

import java.util.List;

public class NodeWriter3D {

    private final DataWriter dataWriter = new DataWriter();
    private final DataWriter indexWriter = new DataWriter();

    private int vertices = 0;
    private int indexOffset = 0;

    private void writeVertex(Vertex3D vertex) {
        getDataWriter().write(vertex.getPosition3f());
        getDataWriter().write(vertex.getColour3f());
        this.vertices++;
    }

    private void writeIndex(int index) {
        getIndexWriter().write(this.indexOffset + index);
    }

    public void write(Node3D node) {
        node.getVertices().getVertices().forEach(this::writeVertex);
        node.getIndices().getIndices().forEach(this::writeIndex);
        this.indexOffset = this.vertices;
    }

    public void write(List<Node3D> nodes) {
        nodes.forEach(this::write);
    }

    public DataWriter getDataWriter() {
        return this.dataWriter;
    }

    public DataWriter getIndexWriter() {
        return this.indexWriter;
    }
}
