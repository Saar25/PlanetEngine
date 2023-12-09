package org.saar.maths.objects

import org.joml.Intersectionf
import org.joml.Vector3fc

class Planef(point: Vector3fc, normal: Vector3fc) {
    val a: Float = normal.x()
    val b: Float = normal.y()
    val c: Float = normal.z()
    val d: Float = -point.dot(normal)

    fun distance(point: Vector3fc): Float {
        return distance(point.x(), point.y(), point.z())
    }

    fun distance(px: Float, py: Float, pz: Float): Float {
        return Intersectionf.distancePointPlane(
            px, py, pz, this.a, this.b, this.c, this.d)
    }
}
