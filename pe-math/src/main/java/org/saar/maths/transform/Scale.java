package org.saar.maths.transform;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.utils.Vector3;

public class Scale {

    private final Vector3f value;

    private Scale(Vector3f value) {
        this.value = value;
    }

    public static Scale of(float x, float y, float z) {
        return new Scale(Vector3.of(x, y, z));
    }

    public static Scale create() {
        return new Scale(Vector3.of(1));
    }

    public void scaleBy(float scale) {
        this.value.mul(scale);
    }

    public void scaleBy(Vector3f scale) {
        this.value.mul(scale);
    }

    public Vector3fc getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Scale{" + getValue() + '}';
    }
}
