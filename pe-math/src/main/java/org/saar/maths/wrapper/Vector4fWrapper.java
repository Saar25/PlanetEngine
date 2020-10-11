package org.saar.maths.wrapper;

import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.saar.maths.utils.Vector4;

public class Vector4fWrapper {

    private final Vector4f value = Vector4.create();
    private final Vector4f readonly = Vector4.create();

    public Vector4f getValue() {
        return this.value;
    }

    public Vector4fc getReadonly() {
        this.readonly.set(getValue());
        return this.readonly;
    }
}
