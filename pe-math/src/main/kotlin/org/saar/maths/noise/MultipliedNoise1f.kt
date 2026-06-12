package org.saar.maths.noise

class MultipliedNoise1f(private val multiply: Int, private val noise1f: Noise1f) : Noise1f {

    override fun noise(x: Float) = this.noise1f.noise(x) * this.multiply
}
