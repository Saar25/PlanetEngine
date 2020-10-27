package org.saar.core.model.mesh.writers;

import org.saar.core.node.Node;

public interface MeshInstanceWriter<T extends Node> {

    void writeInstance(T instance);

    default void writeInstances(T[] instances) {
        for (T instance : instances) {
            writeInstance(instance);
        }
    }

}
