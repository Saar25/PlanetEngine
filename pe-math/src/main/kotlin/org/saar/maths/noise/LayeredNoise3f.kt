package org.saar.maths.noise

import kotlin.math.pow

class LayeredNoise3f(private val noise3f: Noise3f, private val layers: Int) : Noise3f {

    override fun noise(x: Float, y: Float, z: Float): Float {
        val noise = (0 until this.layers).sumOf {
            val pow2 = 2.0.pow(it.toDouble()).toFloat()
            (this.noise3f.noise(x / pow2, y / pow2, z / pow2) * pow2).toDouble()
        }
        return (noise / (2.0.pow(this.layers.toDouble()) - 1)).toFloat()
    }
}
