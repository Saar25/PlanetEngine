package org.saar.core.model.vertex;

import org.saar.core.model.Vertex;

public interface ModelBufferWriter<T extends Vertex> {

    void write(ModelVertices<T> vertices);

    void write(ModelIndices indices);

    ModelBuffer toBuffer();

}
