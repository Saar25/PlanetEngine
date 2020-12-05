package org.saar.maths.transform;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jproperty.ChangeEvent;
import org.jproperty.ChangeEventBase;
import org.jproperty.ChangeListener;
import org.jproperty.ListenersHelper;
import org.saar.maths.utils.Vector3;

public class Position implements ReadonlyPosition {

    private final Vector3f value = Vector3.create();

    private ListenersHelper<Vector3fc> helper = ListenersHelper.empty();

    private Position(Vector3fc value) {
        this.value.set(value);
    }

    public static Position of(float x, float y, float z) {
        return new Position(Vector3.of(x, y, z));
    }

    public static Position of(Vector3fc position) {
        return new Position(Vector3.of(position));
    }

    public static Position create() {
        return new Position(Vector3.create());
    }

    private Vector3fc copyValue() {
        return Vector3.of(getValue());
    }

    public void add(Vector3fc direction) {
        final Vector3fc old = copyValue();
        this.value.add(direction);
        onChange(old);
    }

    public void sub(Vector3fc direction) {
        final Vector3fc old = copyValue();
        this.value.sub(direction);
        onChange(old);
    }

    public void set(ReadonlyPosition position) {
        final Vector3fc old = copyValue();
        this.value.set(position.getValue());
        onChange(old);
    }

    public void set(Vector3fc position) {
        final Vector3fc old = copyValue();
        this.value.set(position);
        onChange(old);
    }

    public void set(float x, float y, float z) {
        final Vector3fc old = copyValue();
        this.value.set(x, y, z);
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
        return this.value;
    }

    public float getX() {
        return this.value.x;
    }

    public float getY() {
        return this.value.y;
    }

    public float getZ() {
        return this.value.z;
    }

    @Override
    public String toString() {
        return "Position{" + getValue() + '}';
    }
}
