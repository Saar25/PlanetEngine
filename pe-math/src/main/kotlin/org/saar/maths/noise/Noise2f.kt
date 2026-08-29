package org.saar.maths.noise

import org.joml.SimplexNoise
import org.joml.Vector2fc

fun interface Noise2f {
    fun noise(x: Float, y: Float): Float

    companion object {
        val simplex = Noise2f(SimplexNoise::noise)
    }
}

fun Noise2f.noise(v: Vector2fc) = noise(v.x(), v.y())