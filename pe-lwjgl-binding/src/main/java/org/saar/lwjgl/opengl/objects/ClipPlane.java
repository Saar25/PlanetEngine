package org.saar.lwjgl.opengl.objects;

import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.saar.maths.utils.Vector4;

public class ClipPlane {

    public static final ClipPlane NONE = ClipPlane.of(0, 0, 0, 0);

    private final Vector4fc value;

    private ClipPlane(Vector4f value) {
        this.value = value;
    }

    public static ClipPlane of(Vector4fc value) {
        return new ClipPlane(Vector4.of(value).normalize());
    }

    public static ClipPlane of(float a, float b, float c, float d) {
        return new ClipPlane(Vector4.of(a, b, c, d).normalize());
    }

    public static ClipPlane ofAbove(float height) {
        return ClipPlane.of(0, +1, 0, -height);
    }

    public static ClipPlane ofBelow(float height) {
        return ClipPlane.of(0, -1, 0, +height);
    }

    public Vector4fc getValue() {
        return this.value;
    }
}