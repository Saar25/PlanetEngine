package org.saar.core.renderer.basic;

import org.saar.core.model.data.DataWriter;

import java.util.List;

public class BasicNodeWriter {

    private final DataWriter dataWriter;
    private final DataWriter indexWriter;

    private int vertices = 0;
    private int indexOffset = 0;

    public BasicNodeWriter(DataWriter dataWriter, DataWriter indexWriter) {
        this.dataWriter = dataWriter;
        this.indexWriter = indexWriter;
    }

    private void writeVertex(BasicVertex vertex) {
        this.dataWriter.write(vertex.getPosition2f().x());
        this.dataWriter.write(vertex.getPosition2f().y());
        this.dataWriter.write(vertex.getColour3f().x());
        this.dataWriter.write(vertex.getColour3f().y());
        this.dataWriter.write(vertex.getColour3f().z());
        this.vertices++;
    }

    private void writeIndex(int index) {
        this.indexWriter.write(this.indexOffset + index);
    }

    public void write(BasicNode node) {
        this.indexOffset = this.vertices;
        node.getVertices().getVertices().forEach(this::writeVertex);
        node.getIndices().getIndices().forEach(this::writeIndex);
    }

    public void write(List<BasicNode> nodes) {
        nodes.forEach(this::write);
    }
}
