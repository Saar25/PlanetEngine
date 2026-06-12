package org.saar.maths.objects

import org.joml.Vector3fc
import org.saar.maths.utils.Vector3
import kotlin.math.abs

class Plane {
    val normal: Vector3fc
    val cp: Float

    constructor(normal: Vector3fc, d: Float) {
        val length = normal.length()
        this.normal = Vector3.of(normal).div(length)
        this.cp = d / length
    }

    constructor(a: Float, b: Float, c: Float, d: Float) {
        val length = Vector3.length(a, b, c)
        this.normal = Vector3.of(a, b, c).div(length)
        this.cp = d / length
    }

    constructor(p1: Vector3fc, p2: Vector3fc, p3: Vector3fc) {
        val p21: Vector3fc = Vector3.sub(p2, p1)
        val p31: Vector3fc = Vector3.sub(p3, p1)
        this.normal = Vector3.cross(p21, p31).normalize()
        this.cp = -normal.dot(p1)
    }

    fun distance(point: Vector3fc?): Float {
        return abs(normal.dot(point) + cp)
    }

    fun signedDistance(point: Vector3fc?): Float {
        return this.normal.dot(point) + cp
    }

    fun isFrontFacing(point: Vector3fc?): Boolean {
        return this.normal.dot(point) > 0
    }
}
