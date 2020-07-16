package org.saar.core.model.vertex;

public abstract class AbstractModelBufferWriter<T extends ModelVertex> implements ModelBufferWriter<T> {

    @Override
    public void write(ModelVertices<T> vertex) {
        
    }

    @Override
    public void write(ModelIndices indices) {

    }

    protected abstract void write(T vertex);
}
