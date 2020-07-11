package org.saar.core.model.vertex;

public class ModelIndices {

    private int[] indices;

    public ModelIndices(int... indices) {
        this.indices = indices;
    }

    public int[] get() {
        return indices;
    }
}
