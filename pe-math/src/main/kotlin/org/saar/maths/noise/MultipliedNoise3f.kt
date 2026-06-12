package org.saar.maths.noise

class MultipliedNoise3f(private val multiply: Int, private val noise3f: Noise3f) : Noise3f {

    override fun noise(x: Float, y: Float, z: Float) = this.noise3f.noise(x, y, z) * this.multiply
}
