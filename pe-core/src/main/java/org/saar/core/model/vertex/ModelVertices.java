package org.saar.core.model.vertex;

import java.util.Arrays;
import java.util.List;

public class ModelVertices {

    private final List<ModelVertex> vertices;

    public ModelVertices(ModelVertex... vertices) {
        this.vertices = Arrays.asList(vertices);
    }

    public void write(ModelBuffer buffer) {
        for (ModelVertex vertex : this.vertices) {
            vertex.write(buffer);
        }
    }

    public int count() {
        return this.vertices.size();
    }
}
