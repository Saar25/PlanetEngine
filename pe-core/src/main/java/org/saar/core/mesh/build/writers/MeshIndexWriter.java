package org.saar.core.mesh.build.writers;

public interface MeshIndexWriter {

    void writeIndex(int index);

    default void writeIndices(int[] indices) {
        for (int index : indices) {
            writeIndex(index);
        }
    }

}