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
        final Vector3f value = Vector3.of(x, y, z);
        return new Position(value);
    }

    public static Position create() {
        final Vector3f position = Vector3.create();
        return new Position(position);
    }

    public void add(Position position) {
        this.value.add(position.getValue());
    }

    public void sub(Position position) {
        this.value.sub(position.getValue());
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
