package org.saar.maths.objects

import org.joml.Vector3fc
import org.saar.maths.utils.Vector3

class Ellipsoid(val position: Vector3fc, val dimensions: Vector3fc) {

    constructor() : this(Vector3.of(0f, 0f, 0f), Vector3.of(1f, 1f, 1f))

    fun toSpace(space: Vector3fc): Ellipsoid {
        if (space.equals(Vector3.ONE, 0.01f)) return this
        val position = Vector3.of(this.position).div(space)
        val dimensions = Vector3.of(this.dimensions).div(space)
        return Ellipsoid(position, dimensions)
    }
}
