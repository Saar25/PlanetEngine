package org.saar.core.model.vertex;

import java.util.ArrayList;
import java.util.List;

public class ModelIndices {

    private final List<Integer> indices = new ArrayList<>();
    private final int[] indicesArray;

    public ModelIndices(int... indices) {
        this.indicesArray = indices;
        for (int index : indices) {
            this.indices.add(index);
        }
    }

    public void write(ModelBuffer buffer) {
        for (int index : this.indices) {
            buffer.writeIndex(index);
        }
    }

    public List<Integer> getIndices() {
        return this.indices;
    }

    public int[] getIndicesArray() {
        return this.indicesArray;
    }

    public int count() {
        return this.indices.size();
    }
}
