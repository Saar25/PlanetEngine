package org.saar.core.renderer.basic;

import org.saar.core.model.data.DataWriter;
import org.saar.core.model.data.NodeWriter;

public class BasicNodeWriter implements NodeWriter<BasicNode> {

    private final DataWriter dataWriter;
    private final DataWriter indexWriter;

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
    }

    private void writeIndex(int index) {
        this.indexWriter.write(index);
    }

    @Override
    public void write(BasicNode node) {
        node.getVertices().getVertices().forEach(this::writeVertex);
        node.getIndices().getIndices().forEach(this::writeIndex);
    }
}
