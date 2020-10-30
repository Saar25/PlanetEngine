package org.saar.core.model.mesh.writers;

import org.saar.core.model.Instance;

public interface MeshInstanceWriter<T extends Instance> {

    void writeInstance(T instance);

    default void writeInstances(T[] instances) {
        for (T instance : instances) {
            writeInstance(instance);
        }
    }

}
