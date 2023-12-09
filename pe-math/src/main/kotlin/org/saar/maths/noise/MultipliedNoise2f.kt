package org.saar.maths.noise

class MultipliedNoise2f(private val multiply: Int, private val noise2f: Noise2f) : Noise2f {

    override fun noise(x: Float, y: Float) = this.noise2f.noise(x, y) * this.multiply
}
