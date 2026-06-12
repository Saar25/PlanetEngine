package org.saar.maths.noise

import org.joml.Vector2fc

fun interface Noise2f {
    fun noise(x: Float, y: Float): Float
}

fun Noise2f.noise(v: Vector2fc) = noise(v.x(), v.y())