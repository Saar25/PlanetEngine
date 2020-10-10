package org.saar.core.model.mesh.writers;

import org.saar.core.model.Vertex;

public interface MeshVertexWriter<T extends Vertex> {

    void writeVertex(T vertex);

}
