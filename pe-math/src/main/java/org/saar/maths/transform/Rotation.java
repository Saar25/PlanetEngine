package org.saar.maths.transform;

import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jproperty.*;
import org.saar.maths.utils.Quaternion;
import org.saar.maths.utils.Vector3;
import org.saar.maths.wrapper.QuaternionfWrapper;

public class Rotation implements ReadOnlyProperty<Quaternionfc> {

    private ListenersHelper<Quaternionfc> helper = ListenersHelper.empty();

    private final QuaternionfWrapper wrapper = new QuaternionfWrapper();
    private final Vector3f eulerAngles = Vector3.create();

    private Rotation(Quaternionfc value) {
        this.wrapper.getValue().set(value);
    }

    public static Rotation fromEulerAngles(Vector3fc eulerAngles) {
        return fromEulerAngles(eulerAngles.x(), eulerAngles.y(), eulerAngles.z());
    }

    public static Rotation fromEulerAngles(float x, float y, float z) {
        return new Rotation(Quaternion.create().rotateXYZ(x, y, z));
    }

    public static Rotation fromQuaternion(Quaternionfc quaternion) {
        return new Rotation(Quaternion.of(quaternion));
    }

    public static Rotation fromQuaternion(float x, float y, float z, float w) {
        return new Rotation(Quaternion.of(x, y, z, w));
    }

    public static Rotation create() {
        return new Rotation(Quaternion.create());
    }

    private Quaternionfc copyValue() {
        return Quaternion.of(this.wrapper.getValue());
    }

    public void set(Rotation rotation) {
        final Quaternionfc old = copyValue();
        this.wrapper.getValue().set(rotation.getValue());
        onChange(old);
    }

    public void set(Quaternionfc rotation) {
        final Quaternionfc old = copyValue();
        this.wrapper.getValue().set(rotation);
        onChange(old);
    }

    private void onChange(Quaternionfc old) {
        if (!old.equals(getValue())) {
            final ChangeEvent<Quaternionfc> event =
                    new ChangeEventBase<>(this, old, getValue());
            this.helper.fireEvent(event);
        }
    }

    @Override
    public void addListener(ChangeListener<? super Quaternionfc> changeListener) {
        this.helper = this.helper.addListener(changeListener);
    }

    @Override
    public void removeListener(ChangeListener<? super Quaternionfc> changeListener) {
        this.helper = this.helper.removeListener(changeListener);
    }

    @Override
    public Quaternionfc getValue() {
        return this.wrapper.getReadonly();
    }

    public Vector3fc getEulerAngles() {
        final Quaternionfc value = this.wrapper.getValue();
        return value.getEulerAnglesXYZ(this.eulerAngles);
    }

    @Override
    public String toString() {
        return "Rotation{" + getValue() + '}';
    }
}
