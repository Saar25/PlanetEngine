package org.saar.core.model.data;

import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;

import java.util.ArrayList;
import java.util.List;

public class DataWriter {

    private final List<Integer> data = new ArrayList<>();

    public void write(int value) {
        this.data.add(value);
    }

    public void write(float value) {
        this.data.add(Float.floatToIntBits(value));
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

    private int[] dataArray() {
        final int[] data = new int[this.data.size()];
        for (int i = 0; i < this.data.size(); i++) {
            data[i] = this.data.get(i);
        }
        return data;
    }
}
