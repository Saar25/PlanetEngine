package org.saar.core.renderer.basic;

import org.saar.core.model.data.DataWriter;

import java.util.List;

public class BasicNodeWriter {

    private final DataWriter dataWriter = new DataWriter();
    private final DataWriter indexWriter = new DataWriter();

    private int vertices = 0;
    private int indexOffset = 0;

    private void writeVertex(BasicVertex vertex) {
        getDataWriter().write(vertex.getPosition2f());
        getDataWriter().write(vertex.getColour3f());
        this.vertices++;
    }

    private void writeIndex(int index) {
        getIndexWriter().write(this.indexOffset + index);
    }

    private void writeInstance(BasicNode instance) {

    }

    public void write(BasicMesh mesh, BasicNode node) {
        this.writeInstance(node);
        mesh.getVertices().getVertices().forEach(this::writeVertex);
        mesh.getIndices().getIndices().forEach(this::writeIndex);
        this.indexOffset = this.vertices;
    }

    public void write(BasicMesh mesh, List<BasicNode> nodes) {
        for (BasicNode node : nodes) {
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
