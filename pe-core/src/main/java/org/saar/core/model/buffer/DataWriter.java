package org.saar.core.model.buffer;

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

    public List<Integer> getData() {
        return data;
    }
}
