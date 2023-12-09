package org.saar.maths.noise

import org.joml.Vector3fc

fun interface Noise3f {
    fun noise(x: Float, y: Float, z: Float): Float
}

fun Noise3f.noise(v: Vector3fc) = noise(v.x(), v.y(), v.z())
