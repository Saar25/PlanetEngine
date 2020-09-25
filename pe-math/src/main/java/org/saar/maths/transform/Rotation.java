package org.saar.maths.transform;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.utils.Quaternion;
import org.saar.maths.utils.Vector3;
import org.saar.maths.wrapper.QuaternionfWrapper;

public class Rotation {

    private final QuaternionfWrapper wrapper = new QuaternionfWrapper();
    private final Vector3f eulerAngles = Vector3.create();

    private Rotation(Quaternionf value) {
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

    public void set(Rotation rotation) {
        this.wrapper.getValue().set(rotation.getValue());
    }

    public void set(Quaternionf rotation) {
        this.wrapper.getValue().set(rotation);
    }

    public Vector3fc getEulerAngles() {
        return this.wrapper.getValue().getEulerAnglesXYZ(this.eulerAngles);
    }

    public Quaternionfc getValue() {
        return this.wrapper.getReadonly();
    }

    @Override
    public String toString() {
        return "Rotation{" + getValue() + '}';
    }
}
