package org.saar.maths.utils;

import org.joml.Vector2f;
import org.joml.Vector2fc;

public final class Vector2 {

    public static final ObjectPool<Vector2f> pool = new ObjectPool<>(Vector2::create);

    private Vector2() {

    }

    public static Vector2f create() {
        return new Vector2f();
    }

    public static Vector2f of(Vector2fc v) {
        return new Vector2f(v.x(), v.y());
    }

    public static Vector2f of(float x, float y) {
        return new Vector2f(x, y);
    }

    public static Vector2f of(float d) {
        return Vector2.of(d, d);
    }

    public static Vector2f right() {
        return Vector2.of(1, 0);
    }
}
