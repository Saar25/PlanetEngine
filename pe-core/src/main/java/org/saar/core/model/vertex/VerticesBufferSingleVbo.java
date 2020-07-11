package org.saar.core.model.vertex;

import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerticesBufferSingleVbo implements VerticesBuffer {

    private final int[] indices;
    private final List<Integer> data = new ArrayList<>();
    private final List<VertexBufferAttribute> vertexAttributes = new ArrayList<>();

    public VerticesBufferSingleVbo(int[] indices, VertexBufferAttribute... vertexAttributes) {
        Collections.addAll(this.vertexAttributes, vertexAttributes);
        this.indices = indices;
    }

    @Override
    public void write(int value) {
        this.data.add(value);
    }

    @Override
    public void write(float value) {
        this.data.add(Float.floatToIntBits(value));
    }

    @Override
    public Vao vao() {
        final Vao vao = Vao.create();
        vao.loadIndexBuffer(indexBuffer());
        vao.loadDataBuffer(dataBuffer(), attributes());
        return vao;
    }

    private IndexBuffer indexBuffer() {
        final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);
        indexBuffer.allocateInt(this.indices.length);
        indexBuffer.storeData(0, this.indices);
        return indexBuffer;
    }

    private DataBuffer dataBuffer() {
        final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
        dataBuffer.allocateData(4 * this.data.size());
        dataBuffer.storeData(0, dataArray());
        return dataBuffer;
    }

    private Attribute[] attributes() {
        final Attribute[] attributes = new Attribute[this.vertexAttributes.size()];
        for (int i = 0; i < this.vertexAttributes.size(); i++) {
            final VertexBufferAttribute current = this.vertexAttributes.get(i);
            attributes[i] = Attribute.of(i, current.getComponentCount(),
                    current.getDataType(), current.isNormalized());
        }
        return attributes;
    }

    private int[] dataArray() {
        final int[] array = new int[this.data.size()];
        for (int i = 0; i < this.data.size(); i++) {
            array[i] = this.data.get(i);
        }
        return array;
    }
}
