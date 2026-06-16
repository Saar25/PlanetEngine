package org.saar.maths.noise

import org.joml.SimplexNoise

fun interface Noise1f {
    fun noise(x: Float): Float

    companion object {
        val simplex = Noise2f(SimplexNoise::noise)
    }
}
