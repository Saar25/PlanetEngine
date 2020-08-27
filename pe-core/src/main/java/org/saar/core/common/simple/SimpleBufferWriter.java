package org.saar.core.common.simple;

import org.saar.core.model.DataWriter;
import org.saar.core.model.vertex.ModelAttribute;
import org.saar.core.model.vertex.ModelBufferWriter;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.core.model.vertex.ModelVertices;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;

public class SimpleBufferWriter implements ModelBufferWriter<SimpleVertex> {

    private final DataWriter dataWriter = new DataWriter();
    private final DataWriter indexWriter = new DataWriter();

    private final Attribute[] attributes;

    public SimpleBufferWriter(ModelAttribute... attributes) {
        this.attributes = new Attribute[attributes.length];
        for (int i = 0; i < attributes.length; i++) {
            final ModelAttribute attribute = attributes[i];
            this.attributes[i] = Attribute.of(i, attribute.getComponentCount(),
                    attribute.getDataType(), attribute.isNormalized());
        }
    }


    @Override
    public void write(ModelVertices<SimpleVertex> vertices) {
        vertices.getVertices().forEach(this::writeVertex);
    }

    @Override
    public void write(ModelIndices indices) {
        indices.getIndices().forEach(this::writeIndex);
    }

    protected void writeVertex(SimpleVertex vertex) {
        for (float value : vertex.getData()) {
            this.dataWriter.write(value);
        }
    }

    protected void writeIndex(int index) {
        this.indexWriter.write(index);
    }

    public Vao toVao() {
        final Vao vao = Vao.create();

        final int[] vertexData = this.dataWriter.getDataArray();
        if (vertexData.length > 0) {
            final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
            dataBuffer.allocateInt(vertexData.length);
            dataBuffer.storeData(0, vertexData);
            vao.loadDataBuffer(dataBuffer, this.attributes);
        }

        final int[] indexData = this.indexWriter.getDataArray();
        if (indexData.length > 0) {
            final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);
            indexBuffer.allocateInt(indexData.length);
            indexBuffer.storeData(0, indexData);
            vao.loadIndexBuffer(indexBuffer, true);
        }

        return vao;
    }
}
