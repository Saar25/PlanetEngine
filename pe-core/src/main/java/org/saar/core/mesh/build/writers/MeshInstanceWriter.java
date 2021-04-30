package org.saar.core.mesh.build.writers;

import org.saar.core.mesh.Instance;

public interface MeshInstanceWriter<T extends Instance> {

    void writeInstance(T instance);

    default void writeInstances(T[] instances) {
        for (T instance : instances) {
            writeInstance(instance);
        }
    }

    default void writeInstances(Iterable<T> instances) {
        for (T instance : instances) {
            writeInstance(instance);
        }
    }

}
