package org.saar.maths.objects;

import org.joml.Intersectionf;
import org.joml.Vector3fc;

public class Planef {

    public final float a;
    public final float b;
    public final float c;
    public final float d;

    public Planef(Vector3fc point, Vector3fc normal) {
        this.a = normal.x();
        this.b = normal.y();
        this.c = normal.z();
        this.d = -point.dot(normal);
    }

    public float distance(Vector3fc point) {
        return distance(point.x(), point.y(), point.z());
    }

    public float distance(float px, float py, float pz) {
        return Intersectionf.distancePointPlane(
                px, py, pz, this.a, this.b, this.c, this.d);
    }
}
