package org.saar.core.model.vertex;

import java.util.Arrays;
import java.util.List;

public class ModelVertices<T extends ModelVertex> {

    private final List<T> vertices;

    @SafeVarargs
    public ModelVertices(T... vertices) {
        this.vertices = Arrays.asList(vertices);
    }

    public void write(ModelBuffer buffer) {
        for (T vertex : this.vertices) {
            vertex.write(buffer);
        }
    }

    public int count() {
        return this.vertices.size();
    }
}
