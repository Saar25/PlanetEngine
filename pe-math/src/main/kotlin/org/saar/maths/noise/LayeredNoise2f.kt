package org.saar.maths.noise

import kotlin.math.pow

class LayeredNoise2f(private val noise2f: Noise2f, private val layers: Int) : Noise2f {

    override fun noise(x: Float, y: Float): Float {
        val noise = (0 until this.layers).sumOf {
            val pow2 = 2.0.pow(it.toDouble()).toFloat()
            (this.noise2f.noise(x / pow2, y / pow2) * pow2).toDouble()
        }
        return (noise / (2.0.pow(this.layers.toDouble()) - 1)).toFloat()
    }
}
