package org.saar.core.model.data;

import org.joml.*;
import org.saar.lwjgl.opengl.objects.Vbo;

import java.util.ArrayList;
import java.util.List;

public class VboBuilder {

    private final List<Integer> data = new ArrayList<>();

    public void write(float value) {
        this.data.add(Float.floatToIntBits(value));
    }

    public void write(Vector2fc value) {
        this.write(value.x());
        this.write(value.y());
    }

    public void write(Vector3fc value) {
        this.write(value.x());
        this.write(value.y());
        this.write(value.z());
    }

    public void write(Vector4fc value) {
        this.write(value.x());
        this.write(value.y());
        this.write(value.z());
        this.write(value.w());
    }

    public void write(int value) {
        this.data.add(value);
    }

    public void write(Vector2ic value) {
        this.write(value.x());
        this.write(value.y());
    }

    public void write(Vector3ic value) {
        this.write(value.x());
        this.write(value.y());
        this.write(value.z());
    }

    public void write(Vector4ic value) {
        this.write(value.x());
        this.write(value.y());
        this.write(value.z());
        this.write(value.w());
    }

    public void writeTo(Vbo buffer) {
        final int[] data = dataArray();
        buffer.allocateInt(data.length);
        buffer.storeData(0, data);
    }

    private int[] dataArray() {
        /*final int size = this.data.size();
        final int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = this.data.get(i);
        }
        return data;*/
        return this.data.stream().mapToInt(
                value -> value).toArray();
    }

}
