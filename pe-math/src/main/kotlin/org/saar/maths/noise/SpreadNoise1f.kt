package org.saar.maths.noise

class SpreadNoise1f(private val noise1f: Noise1f, private val division: Float) : Noise1f {

    override fun noise(x: Float) = this.noise1f.noise(x / this.division)
}
