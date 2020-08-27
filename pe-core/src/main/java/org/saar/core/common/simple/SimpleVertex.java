package org.saar.core.common.simple;

import org.saar.core.model.Vertex;

public class SimpleVertex implements Vertex {

    private final float[] floatData;

    public SimpleVertex(float... floatData) {
        this.floatData = floatData;
    }

    public float[] getData() {
        return this.floatData;
    }
}
