package org.saar.core.renderer.basic;

import org.saar.core.model.data.DataWriter;
import org.saar.core.model.data.NodeWriter;
import org.saar.core.model.vertex.ModelAttribute;
import org.saar.core.model.vertex.ModelAttributes;
import org.saar.lwjgl.opengl.constants.DataType;

public class BasicBufferWriter implements NodeWriter<BasicNode> {

    private final ModelAttributes attributes = new ModelAttributes(
            new ModelAttribute(2, true, DataType.FLOAT),
            new ModelAttribute(3, true, DataType.FLOAT));

    private final DataWriter dataWriter;
    private final DataWriter indexWriter;

    public BasicBufferWriter(DataWriter dataWriter, DataWriter indexWriter) {
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

    public ModelAttributes getAttributes() {
        return this.attributes;
    }
}
