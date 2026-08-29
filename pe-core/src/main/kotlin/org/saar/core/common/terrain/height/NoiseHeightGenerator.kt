package org.saar.core.common.terrain.height

import org.saar.maths.noise.Noise2f
import org.saar.maths.noise.Noise3f

class NoiseHeightGenerator(private val noise: Noise3f) : HeightGenerator {

    constructor(noise: Noise2f) : this({ x, _, z -> noise.noise(x, z) })

    override fun generate(x: Float, y: Float, z: Float) = this.noise.noise(x, y, z)

}