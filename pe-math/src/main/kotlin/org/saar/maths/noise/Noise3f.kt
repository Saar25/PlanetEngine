package org.saar.maths.noise

import org.joml.SimplexNoise
import org.joml.Vector3fc

fun interface Noise3f {
    fun noise(x: Float, y: Float, z: Float): Float

    companion object {
        val simplex = Noise2f(SimplexNoise::noise)
    }
}

fun Noise3f.noise(v: Vector3fc) = noise(v.x(), v.y(), v.z())
