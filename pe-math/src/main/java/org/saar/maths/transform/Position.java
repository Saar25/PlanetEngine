package org.saar.maths.transform;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.utils.Vector3;

public class Position {

    private final Vector3f value;

    private Position(Vector3f value) {
        this.value = value;
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

    public void add(Vector3fc direction) {
        this.value.add(direction);
    }

    public void sub(Vector3fc direction) {
        this.value.sub(direction);
    }

    public void set(Position position) {
        this.value.set(position.getValue());
    }

    public Vector3fc getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Position{" + getValue() + '}';
    }
}
