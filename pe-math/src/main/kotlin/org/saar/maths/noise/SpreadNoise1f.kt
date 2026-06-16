package org.saar.maths.noise

class SpreadNoise1f(private val division: Float, private val noise1f: Noise1f) : Noise1f {

    override fun noise(x: Float) = this.noise1f.noise(x / this.division)
}
