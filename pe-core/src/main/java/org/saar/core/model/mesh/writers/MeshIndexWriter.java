package org.saar.core.model.mesh.writers;

public interface MeshIndexWriter {

    void writeIndex(int index);

    default void writeIndices(int[] indices) {
        for (int index : indices) {
            writeIndex(index);
        }
    }

}
