package org.saar.core.model.vertex;

public abstract class AbstractModelBufferWriter<T extends ModelVertex> implements ModelBufferWriter<T> {

    @Override
    public void write(ModelVertices<T> vertices) {
        vertices.getVertices().forEach(this::write);
    }

    @Override
    public void write(ModelIndices indices) {
        indices.getIndices().forEach(this::write);
    }

    protected abstract void write(T vertex);

    protected abstract void write(int index);
}
