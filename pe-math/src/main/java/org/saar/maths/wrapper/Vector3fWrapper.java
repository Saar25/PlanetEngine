package org.saar.maths.wrapper;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.utils.Vector3;

public class Vector3fWrapper {

    private final Vector3f value = Vector3.create();
    private final Vector3f readonly = Vector3.create();

    public Vector3f getValue() {
        return this.value;
    }

    public Vector3fc getReadonly() {
        this.readonly.set(getValue());
        return this.readonly;
    }
}
