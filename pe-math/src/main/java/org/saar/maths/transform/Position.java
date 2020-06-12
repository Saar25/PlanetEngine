package org.saar.maths.transform;

import org.saar.maths.joml.Vector3f;
import org.saar.maths.utils.Vector3;

public class Position {

    private final Vector3f value;

    private Position(Vector3f value) {
        this.value = value;
    }

    public static Position of(float x, float y, float z) {
        Vector3f value = Vector3.of(x, y, z);
        return new Position(value);
    }

    public static Position create() {
        Vector3f position = Vector3.create();
        return new Position(position);
    }

    public Vector3f getValue() {
        return value;
    }
}
