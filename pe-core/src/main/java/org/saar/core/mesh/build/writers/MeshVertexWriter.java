package org.saar.core.mesh.build.writers;

import org.saar.core.mesh.Vertex;

public interface MeshVertexWriter<T extends Vertex> {

    void writeVertex(T vertex);

    default void writeVertices(T[] vertices) {
        for (T vertex : vertices) {
            writeVertex(vertex);
        }
    }

    default void writeVertices(Iterable<T> vertices) {
        for (T vertex : vertices) {
            writeVertex(vertex);
        }
    }

}
