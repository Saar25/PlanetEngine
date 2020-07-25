package org.saar.core.model.vertex;

import java.util.Arrays;
import java.util.List;

public class ModelVertices<T extends ModelVertex> {

    private final List<T> vertices;

    @SafeVarargs
    public ModelVertices(T... vertices) {
        this.vertices = Arrays.asList(vertices);
    }

    public List<T> getVertices() {
        return vertices;
    }

    public int count() {
        return this.vertices.size();
    }
}
