package org.saar.maths.noise

class MultipliedNoise1f(private val noise1f: Noise1f, private val multiply: Float) : Noise1f {

    override fun noise(x: Float) = this.noise1f.noise(x) * this.multiply
}
