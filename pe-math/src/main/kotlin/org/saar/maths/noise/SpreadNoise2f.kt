package org.saar.maths.noise

class SpreadNoise2f(private val division: Float, private val noise2f: Noise2f) : Noise2f {

    override fun noise(x: Float, y: Float) = this.noise2f.noise(x / this.division, y / this.division)
}
