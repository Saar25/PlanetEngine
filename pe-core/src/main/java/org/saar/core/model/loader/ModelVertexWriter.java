package org.saar.core.model.loader;

import org.saar.core.model.Vertex;

public interface ModelVertexWriter<T extends Vertex> {

    void writeVertex(T vertex);

}
