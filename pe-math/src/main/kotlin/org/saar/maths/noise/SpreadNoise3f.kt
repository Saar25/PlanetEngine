package org.saar.maths.noise

class SpreadNoise3f(private val noise3f: Noise3f, private val division: Float) : Noise3f {

    override fun noise(x: Float, y: Float, z: Float) =
        this.noise3f.noise(x / this.division, y / this.division, z / this.division)
}
