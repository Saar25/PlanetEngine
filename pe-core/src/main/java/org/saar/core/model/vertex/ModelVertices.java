package org.saar.core.model.vertex;

import org.saar.core.model.Vertex;

import java.util.Arrays;
import java.util.List;

public class ModelVertices<T extends Vertex> {

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
