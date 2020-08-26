package org.saar.core.model.vertex;

import org.saar.core.model.Vertex;

public abstract class AbstractModelBufferWriter<T extends Vertex> implements ModelBufferWriter<T> {

    @Override
    public void write(ModelVertices<T> vertices) {
        vertices.getVertices().forEach(this::writeVertex);
    }

    @Override
    public void write(ModelIndices indices) {
        indices.getIndices().forEach(this::writeIndex);
    }

    protected abstract void writeVertex(T vertex);

    protected abstract void writeIndex(int index);
}
