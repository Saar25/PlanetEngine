package org.saar.core.mesh.writers;

public interface MeshIndexWriter {

    void writeIndex(int index);

    default void writeIndices(int[] indices) {
        for (int index : indices) {
            writeIndex(index);
        }
    }

    default void writeIndices(Iterable<Integer> indices) {
        for (int index : indices) {
            writeIndex(index);
        }
    }

}
