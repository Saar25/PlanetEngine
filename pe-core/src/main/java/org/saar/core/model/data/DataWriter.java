package org.saar.core.model.data;

import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;

import java.util.ArrayList;
import java.util.List;

public class DataWriter {

    private final List<Integer> data = new ArrayList<>();

    public void write(int value) {
        getData().add(value);
    }

    public void write(float value) {
        getData().add(Float.floatToIntBits(value));
    }

    public void writeTo(IndexBuffer buffer) {
        final int[] data = dataArray();
        buffer.allocateInt(data.length);
        buffer.storeData(0, data);
    }

    public void writeTo(DataBuffer buffer) {
        final int[] data = dataArray();
        buffer.allocateInt(data.length);
        buffer.storeData(0, data);
    }

    public List<Integer> getData() {
        return this.data;
    }

    private int[] dataArray() {
        final int[] data = new int[getData().size()];
        for (int i = 0; i < getData().size(); i++) {
            data[i] = getData().get(i);
        }
        return data;
    }
}
