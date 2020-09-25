package org.saar.maths.wrapper;

import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.saar.maths.utils.Matrix3;

public class Matrix3fWrapper {

    private final Matrix3f value = Matrix3.create();
    private final Matrix3f readonly = Matrix3.create();

    public Matrix3f getValue() {
        return this.value;
    }

    public Matrix3fc getReadonly() {
        this.readonly.set(getValue());
        return this.readonly;
    }
}
