package org.saar.core.model.vertex;

public interface ModelBufferWriter<T extends ModelVertex> {

    void write(ModelVertices<T> vertices);

    void write(ModelIndices indices);

    ModelBuffer toBuffer();

}
