package org.saar.maths.transform;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jproperty.*;
import org.saar.maths.utils.Vector3;
import org.saar.maths.wrapper.Vector3fWrapper;

public class Position implements ReadOnlyProperty<Vector3fc> {

    private ListenersHelper<Vector3fc> helper = ListenersHelper.empty();

    private final Vector3fWrapper wrapper = new Vector3fWrapper();

    private Position(Vector3f value) {
        this.wrapper.getValue().set(value);
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
        return Vector3.of(this.wrapper.getValue());
    }

    public void add(Vector3fc direction) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().add(direction);
        onChange(old);
    }

    public void sub(Vector3fc direction) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().sub(direction);
        onChange(old);
    }

    public void set(Position position) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().set(position.getValue());
        onChange(old);
    }

    public void set(Vector3fc position) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().set(position);
        onChange(old);
    }

    public void set(float x, float y, float z) {
        final Vector3fc old = copyValue();
        this.wrapper.getValue().set(x, y, z);
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

    public float getX() {
        return this.wrapper.getValue().x;
    }

    public float getY() {
        return this.wrapper.getValue().y;
    }

    public float getZ() {
        return this.wrapper.getValue().z;
    }

    @Override
    public String toString() {
        return "Position{" + getValue() + '}';
    }
}
