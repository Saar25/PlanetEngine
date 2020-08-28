package org.saar.core.common.simple;

import org.lwjgl.system.MemoryUtil;
import org.saar.core.model.vertex.ModelAttribute;
import org.saar.core.model.vertex.ModelBufferWriter;
import org.saar.core.model.vertex.ModelIndices;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.lwjgl.opengl.utils.MemoryUtils;

import java.nio.ByteBuffer;
import java.util.List;

public class SimpleBufferWriter implements ModelBufferWriter<SimpleVertex> {

    private ByteBuffer data;
    private BufferWriter dataWriter;
    private ByteBuffer indexData;
    private BufferWriter indexWriter;

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
    public void write(List<SimpleVertex> vertices) {
        data = MemoryUtils.allocByte(vertices.size() * vertices.get(0).getData().length * 4);
        dataWriter = new BufferWriter(data);
        vertices.forEach(this::writeVertex);
    }

    @Override
    public void write(ModelIndices indices) {
        indexData = MemoryUtils.allocByte(indices.count() * 4);
        indexWriter = new BufferWriter(indexData);
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

        if (data.position() > 0) {
            data.flip();
            final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
            dataBuffer.allocateInt(data.limit());
            dataBuffer.storeData(0, data);
            vao.loadVbo(dataBuffer, this.attributes);
        }

        if (indexData.position() > 0) {
            indexData.flip();
            final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);
            indexBuffer.allocateInt(indexData.limit());
            indexBuffer.storeData(0, indexData);
            vao.loadVbo(indexBuffer);
        }

        MemoryUtil.memFree(data);
        MemoryUtil.memFree(indexData);

        return vao;
    }
}
