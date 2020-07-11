package org.saar.maths.transform;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.utils.Quaternion;
import org.saar.maths.utils.Vector3;

public class Rotation {

    private final Quaternionf value;
    private final Vector3f eulerAngles;

    private Rotation(Quaternionf value) {
        this.value = value;
        this.eulerAngles = Vector3.create();
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
        this.value.set(rotation.getValue());
    }

    public Vector3fc getEulerAngles() {
        return this.value.getEulerAnglesXYZ(this.eulerAngles);
    }

    public Quaternionfc getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Rotation{" + getValue() + '}';
    }
}
