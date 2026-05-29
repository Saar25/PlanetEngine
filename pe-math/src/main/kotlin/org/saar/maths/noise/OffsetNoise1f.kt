package org.saar.maths.noise

class OffsetNoise1f(private val offset: Int, private val noise1f: Noise1f) : Noise1f {

    override fun noise(x: Float) = this.noise1f.noise(x) + this.offset
}
