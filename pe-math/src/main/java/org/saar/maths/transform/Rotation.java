package org.saar.maths.transform;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.saar.maths.utils.Quaternion;

public class Rotation {

    private final Quaternionf value;

    private Rotation(Quaternionf value) {
        this.value = value;
    }

    public static Rotation of(float x, float y, float z) {
        Quaternionf value = Quaternion.create().rotateXYZ(x, y, z);
        return new Rotation(value);
    }

    public static Rotation of(float x, float y, float z, float w) {
        Quaternionf value = Quaternion.of(x, y, z, w);
        return new Rotation(value);
    }

    public static Rotation create() {
        Quaternionf value = Quaternion.create();
        return new Rotation(value);
    }

    public Vector3f getEulerAngles(Vector3f dest) {
        return value.getEulerAnglesXYZ(dest);
    }

    public Quaternionf getValue() {
        return value;
    }
}
