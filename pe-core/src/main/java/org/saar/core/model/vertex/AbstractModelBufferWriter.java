package org.saar.core.model.vertex;

public abstract class AbstractModelBufferWriter<T extends ModelVertex> implements ModelBufferWriter<T> {

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
