package org.saar.maths.wrapper;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.maths.utils.Matrix4;

public class Matrix4fWrapper {

    private final Matrix4f value = Matrix4.create();
    private final Matrix4f readonly = Matrix4.create();

    public Matrix4f getValue() {
        return this.value;
    }

    public Matrix4fc getReadonly() {
        this.readonly.set(getValue());
        return this.readonly;
    }
}
