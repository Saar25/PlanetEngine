package org.saar.maths.transform;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.maths.utils.Vector3;
import org.saar.maths.wrapper.Vector3fWrapper;

public class Position {

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

    public void add(Vector3fc direction) {
        this.wrapper.getValue().add(direction);
    }

    public void sub(Vector3fc direction) {
        this.wrapper.getValue().sub(direction);
    }

    public void set(Position position) {
        this.wrapper.getValue().set(position.getValue());
    }

    public void set(Vector3fc position) {
        this.wrapper.getValue().set(position);
    }

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
