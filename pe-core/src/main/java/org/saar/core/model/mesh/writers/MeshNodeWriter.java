package org.saar.core.model.mesh.writers;

import org.saar.core.node.Node;

public interface MeshNodeWriter<T extends Node> {

    void writeNode(T node);

    default void writeInstances(T[] instances) {
        for (T instance : instances) {
            writeNode(instance);
        }
    }

}
