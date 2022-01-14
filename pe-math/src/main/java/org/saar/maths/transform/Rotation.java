package org.saar.maths.transform;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jproperty.*;
import org.saar.maths.Angle;
import org.saar.maths.utils.Quaternion;
import org.saar.maths.utils.Vector3;

public class Rotation implements ReadonlyRotation {

    private final Quaternionf value = Quaternion.create();
    private final Vector3f eulerAngles = Vector3.create();

    private SubscribersHelper<Quaternionfc> helper = SubscribersHelper.empty();

    private Rotation(Quaternionfc value) {
        this.value.set(value);
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
        return Quaternion.of(this.value);
    }

    public void set(ReadonlyRotation rotation) {
        final Quaternionfc old = copyValue();
        this.value.set(rotation.getValue());
        onChange(old);
    }

    public void set(Quaternionfc rotation) {
        final Quaternionfc old = copyValue();
        this.value.set(rotation);
        onChange(old);
    }

    public void setRotation(Angle x, Angle y, Angle z) {
        final Quaternionfc old = copyValue();
        this.value.rotateXYZ(x.getRadians(),
                y.getRadians(), z.getRadians());
        onChange(old);
    }

    public void rotate(Angle x, Angle y, Angle z) {
        rotateRadians(x.getRadians(), y.getRadians(), z.getRadians());
    }

    public void rotateDegrees(float x, float y, float z) {
        final float xRadians = (float) Math.toRadians(x);
        final float yRadians = (float) Math.toRadians(y);
        final float zRadians = (float) Math.toRadians(z);
        rotateRadians(xRadians, yRadians, zRadians);
    }

    public void rotateRadians(float x, float y, float z) {
        final Quaternionfc old = copyValue();
        this.value.rotateX(x)
                .rotateLocalY(y).rotateZ(z);
        onChange(old);
    }

    public void rotate(Rotation rotation) {
        rotate(rotation.getValue());
    }

    public void rotate(Quaternionfc rotation) {
        final Quaternionfc old = copyValue();
        this.value.mul(rotation);
        onChange(old);
    }

    private void onChange(Quaternionfc old) {
        if (!old.equals(getValue())) {
            final ChangeEvent<Quaternionfc> event =
                    new ChangeEventBase<>(this, old, getValue());
            this.helper.fireEvent(event);
        }
    }

    public void lookAlong(Vector3fc direction) {
        direction = Vector3.normalize(direction);
        if (direction.equals(Vector3.DOWN, 0)) {
            set(Quaternion.of(-1, 0, 0, 1).normalize());
        } else if (!direction.equals(Vector3.ZERO, 0)) {
            set(Quaternion.create().lookAlong(
                    direction, Vector3.UP).conjugate());
        }
    }

    @Override
    public Subscription subscribe(Subscriber<? super Quaternionfc> subscriber) {
        this.helper = this.helper.addSubscriber(subscriber);

        return () -> this.helper = this.helper.removeSubscriber(subscriber);
    }

    @Override
    public Quaternionfc getValue() {
        return this.value;
    }

    @Override
    public Vector3fc getEulerAngles() {
        final Quaternionfc value = this.value;
        return value.getEulerAnglesXYZ(this.eulerAngles);
    }

    @Override
    public Vector3f getDirection() {
        return Vector3.forward().rotate(getValue());
    }

    @Override
    public String toString() {
        return "Rotation{" + getValue() + '}';
    }
}
