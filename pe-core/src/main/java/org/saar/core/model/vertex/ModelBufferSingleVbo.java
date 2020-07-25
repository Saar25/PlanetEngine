package org.saar.core.model.vertex;

import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelBufferSingleVbo implements ModelBuffer {

    private final List<Integer> data = new ArrayList<>();
    private final List<Integer> indices = new ArrayList<>();
    private final List<ModelAttribute> vertexModelAttributes = new ArrayList<>();

    public ModelBufferSingleVbo(ModelAttribute... vertexModelAttributes) {
        Collections.addAll(this.vertexModelAttributes, vertexModelAttributes);
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
    public void writeIndex(int index) {
        this.indices.add(index);
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
        indexBuffer.allocateInt(this.indices.size());
        indexBuffer.storeData(0, indexArray());
        return indexBuffer;
    }

    private DataBuffer dataBuffer() {
        final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
        dataBuffer.allocateData(4 * this.data.size());
        dataBuffer.storeData(0, dataArray());
        return dataBuffer;
    }

    private Attribute[] attributes() {
        final Attribute[] attributes = new Attribute[this.vertexModelAttributes.size()];
        for (int i = 0; i < this.vertexModelAttributes.size(); i++) {
            final ModelAttribute current = this.vertexModelAttributes.get(i);
            attributes[i] = Attribute.of(i, current.getComponentCount(),
                    current.getDataType(), current.isNormalized());
        }
        return attributes;
    }

    private int[] dataArray() {
        return toArray(this.data);
    }

    private int[] indexArray() {
        return toArray(this.indices);
    }

    private int[] toArray(List<Integer> intList) {
        final int[] array = new int[intList.size()];
        for (int i = 0; i < intList.size(); i++) {
            array[i] = intList.get(i);
        }
        return array;
    }
}
