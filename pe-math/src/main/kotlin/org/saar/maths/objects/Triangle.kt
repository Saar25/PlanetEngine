package org.saar.maths.objects

import org.joml.Intersectionf
import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.maths.utils.Maths.barryCentric
import org.saar.maths.utils.Vector3

class Triangle(p1: Vector3fc, p2: Vector3fc, p3: Vector3fc) {
    val p1 = Vector3.of(p1)
    val p2 = Vector3.of(p2)
    val p3 = Vector3.of(p3)

    fun contains(point: Vector3fc): Boolean {
        return Intersectionf.testPointInTriangle(
            point.x(), point.y(), point.z(),
            this.p1.x(), this.p1.y(), this.p1.z(),
            this.p2.x(), this.p2.y(), this.p2.z(),
            this.p3.x(), this.p3.y(), this.p3.z()
        )
    }

    fun contains(x: Float, z: Float): Boolean {
        val A = .5f * (-p2.z() * p3.x() + p1.z() * (-p2.x() + p3.x()) + p1.x() * (p2.z() - p3.z()) + p2.x() * p3.z())
        val sign = (if (A < 0) -1 else 1).toFloat()
        val s = (p1.z() * p3.x() - p1.x() * p3.z() + (p3.z() - p1.z()) * x + (p1.x() - p3.x()) * z) * sign
        val t = (p1.x() * p2.z() - p1.z() * p2.x() + (p1.z() - p2.z()) * x + (p2.x() - p1.x()) * z) * sign
        return s > 0 && t > 0 && (s + t) < 2 * A * sign
    }

    fun getHeight(position: Vector2fc): Float {
        return barryCentric(this.p1, this.p2, this.p3, position)
    }

    fun toSpace(space: Vector3fc): Triangle {
        if (space.equals(Vector3.ONE, 0.01f)) return this
        val p1: Vector3fc = Vector3.of(this.p1).div(space)
        val p2: Vector3fc = Vector3.of(this.p2).div(space)
        val p3: Vector3fc = Vector3.of(this.p3).div(space)
        return Triangle(p1, p2, p3)
    }

    override fun toString(): String {
        return "Triangle(p1=$p1, p2=$p2, p3=$p3)"
    }
}
