package org.saar.maths.noise

import kotlin.math.pow

class LayeredNoise1f(private val noise1f: Noise1f, private val layers: Int) : Noise1f {

    override fun noise(x: Float): Float {
        val noise = (0 until this.layers).sumOf {
            val pow2 = 2.0.pow(it.toDouble()).toFloat()
            (this.noise1f.noise(x / pow2) * pow2).toDouble()
        }
        return (noise / (2.0.pow(this.layers.toDouble()) - 1)).toFloat()
    }
}
