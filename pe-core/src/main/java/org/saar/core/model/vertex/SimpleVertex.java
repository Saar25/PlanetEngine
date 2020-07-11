package org.saar.core.model.vertex;

public class SimpleVertex implements Vertex {

    private final float[] floatData;
    private final int[] intData;

    public SimpleVertex(float[] floatData, int[] intData) {
        this.floatData = floatData;
        this.intData = intData;
    }

    @Override
    public void write(VerticesBuffer verticesBuffer) {
        for (float value : this.floatData) {
            verticesBuffer.write(value);
        }
        for (int value : this.intData) {
            verticesBuffer.write(value);
        }
    }
}
