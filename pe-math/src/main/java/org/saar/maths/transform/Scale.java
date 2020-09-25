package org.saar.maths.transform;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.utils.Vector3;
import org.saar.maths.wrapper.Vector3fWrapper;

public class Scale {

    private final Vector3fWrapper wrapper = new Vector3fWrapper();

    private Scale(Vector3f value) {
        this.wrapper.getValue().set(value);
    }

    public static Scale of(float x, float y, float z) {
        return new Scale(Vector3.of(x, y, z));
    }

    public static Scale create() {
        return new Scale(Vector3.of(1));
    }

    public void scale(float scale) {
        this.wrapper.getValue().mul(scale);
    }

    public void scale(Vector3f scale) {
        this.wrapper.getValue().mul(scale);
    }

    public void set(Scale scale) {
        this.wrapper.getValue().set(scale.getValue());
    }

    public void set(Vector3fc value) {
        this.wrapper.getValue().set(value);
    }

    public void set(float value) {
        this.wrapper.getValue().set(value);
    }

    public Vector3fc getValue() {
        return this.wrapper.getReadonly();
    }

    @Override
    public String toString() {
        return "Scale{" + getValue() + '}';
    }
}
