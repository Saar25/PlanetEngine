package org.saar.maths.transform;

import org.joml.Vector3fc;
import org.jproperty.ChangeEvent;
import org.jproperty.ChangeEventBase;
import org.jproperty.ChangeListener;
import org.jproperty.ListenersHelper;
import org.saar.maths.utils.Vector3;
import org.saar.maths.wrapper.Vector3fWrapper;

public class Scale implements ReadonlyScale {

    private ListenersHelper<Vector3fc> helper = ListenersHelper.empty();

    private final Vector3fWrapper wrapper = new Vector3fWrapper();

    private Scale(Vector3fc value) {
        this.wrapper.getValue().set(value);
    }

    public static Scale of(float x, float y, float z) {
        return new Scale(Vector3.of(x, y, z));
    }

    public static Scale create() {
        return new Scale(Vector3.of(1));
    }

    private Vector3fc copyValue() {
        return Vector3.of(this.wrapper.getValue());
    }

    public void scale(float scale) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().mul(scale);
        onChange(old);
    }

    public void scale(Vector3fc scale) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().mul(scale);
        onChange(old);
    }

    public void set(ReadonlyScale scale) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().set(scale.getValue());
        onChange(old);
    }

    public void set(Vector3fc value) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().set(value);
        onChange(old);
    }

    public void set(float x, float y, float z) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().set(x, y, z);
        onChange(old);
    }

    public void set(float value) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().set(value);
        onChange(old);
    }

    private void onChange(Vector3fc old) {
        if (!old.equals(getValue())) {
            final ChangeEvent<Vector3fc> event =
                    new ChangeEventBase<>(this, old, getValue());
            this.helper.fireEvent(event);
        }
    }

    @Override
    public void addListener(ChangeListener<? super Vector3fc> changeListener) {
        this.helper = this.helper.addListener(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super Vector3fc> changeListener) {
        this.helper = this.helper.removeListener(changeListener);
    }

    @Override
    public Vector3fc getValue() {
        return this.wrapper.getReadonly();
    }

    @Override
    public String toString() {
        return "Scale{" + getValue() + '}';
    }
}
