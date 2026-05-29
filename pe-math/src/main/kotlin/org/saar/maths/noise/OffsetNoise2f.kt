package org.saar.maths.noise

class OffsetNoise2f(private val offset: Int, private val noise2f: Noise2f) : Noise2f {

    override fun noise(x: Float, y: Float) = this.noise2f.noise(x, y) + this.offset
}
