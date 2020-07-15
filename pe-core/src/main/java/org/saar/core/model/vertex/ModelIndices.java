package org.saar.core.model.vertex;

public class ModelIndices {

    private final int[] indices;

    public ModelIndices(int... indices) {
        this.indices = indices;
    }

    public void write(ModelBuffer buffer) {
        for (int index : this.indices) {
            buffer.writeIndex(index);
        }
    }

    public int count() {
        return this.indices.length;
    }
}
