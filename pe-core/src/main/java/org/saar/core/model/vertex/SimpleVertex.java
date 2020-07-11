package org.saar.core.model.vertex;

public class SimpleVertex implements Vertex {

    private final float[] floatData;
    private final int[] intData;

    public SimpleVertex(float[] floatData, int[] intData) {
        this.floatData = floatData;
        this.intData = intData;
    }

    public SimpleVertex(float... floatData) {
        this.floatData = floatData;
        this.intData = new int[0];
    }

    public SimpleVertex(int... intData) {
        this.floatData = new float[0];
        this.intData = intData;
    }

    @Override
    public void write(ModelVertices modelVertices) {
        for (float value : this.floatData) {
            modelVertices.write(value);
        }
        for (int value : this.intData) {
            modelVertices.write(value);
        }
    }
}
