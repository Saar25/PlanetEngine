package org.saar.maths.noise

class OffsetNoise3f(private val noise3f: Noise3f, private val offset: Float) : Noise3f {

    override fun noise(x: Float, y: Float, z: Float) = this.noise3f.noise(x, y, z) + this.offset
}
