package org.saar.maths.transform;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jproperty.value.ObservableValueBase;
import org.saar.maths.utils.Vector3;

public class Scale extends ObservableValueBase<Vector3fc> implements ReadonlyScale {

    private final Vector3f value = Vector3.create();

    private Scale(Vector3fc value) {
        this.value.set(value);
    }

    public static Scale of(float x, float y, float z) {
        return new Scale(Vector3.of(x, y, z));
    }

    public static Scale create() {
        return new Scale(Vector3.of(1));
    }

    private Vector3fc copyValue() {
        return Vector3.of(this.value);
    }

    public void scale(float scale) {
        final Vector3fc old = copyValue();
        this.value.mul(scale);
        onChange(old);
    }

    public void scale(Vector3fc scale) {
        final Vector3fc old = copyValue();
        this.value.mul(scale);
        onChange(old);
    }

    public void scale(float x, float y, float z) {
        final Vector3fc old = copyValue();
        this.value.mul(x, y, z);
        onChange(old);
    }

    public void set(ReadonlyScale scale) {
        final Vector3fc old = copyValue();
        this.value.set(scale.getValue());
        onChange(old);
    }

    public void set(Vector3fc value) {
        final Vector3fc old = copyValue();
        this.value.set(value);
        onChange(old);
    }

    public void set(float x, float y, float z) {
        final Vector3fc old = copyValue();
        this.value.set(x, y, z);
        onChange(old);
    }

    public void set(float value) {
        final Vector3fc old = copyValue();
        this.value.set(value);
        onChange(old);
    }

    private void onChange(Vector3fc old) {
        if (!old.equals(getValue())) {
            fireChangeEvent(old);
        }
    }

    @Override
    public Vector3fc getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Scale{" + getValue() + '}';
    }
}
