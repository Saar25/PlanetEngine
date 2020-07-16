package org.saar.core.model.vertex;

public class SimpleVertex implements ModelVertex {

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

    public float[] getFloatData() {
        return floatData;
    }

    public int[] getIntData() {
        return intData;
    }

    @Override
    public void write(ModelBuffer modelBuffer) {
        for (float value : this.floatData) {
            modelBuffer.write(value);
        }
        for (int value : this.intData) {
            modelBuffer.write(value);
        }
    }
}
