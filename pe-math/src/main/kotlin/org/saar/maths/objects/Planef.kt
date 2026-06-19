package org.saar.maths.objects

import org.joml.*

class Planef(private val normal: Vector3fc, val distance: Float) {

    constructor(normal: Vector3fc, point: Vector3fc) : this(normal, point.dot(normal))

    val a: Float get() = this.normal.x()
    val b: Float get() = this.normal.y()
    val c: Float get() = this.normal.z()

    fun distance(point: Vector3fc): Float {
        return distance(point.x(), point.y(), point.z())
    }

    fun distance(px: Float, py: Float, pz: Float): Float {
        val (a, b, c) = this.normal
        return Intersectionf.distancePointPlane(
            px, py, pz, a, b, c, this.distance
        )
    }
}
