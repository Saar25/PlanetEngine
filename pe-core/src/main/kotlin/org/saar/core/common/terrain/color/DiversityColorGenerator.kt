package org.saar.core.common.terrain.color

import org.joml.Vector3fc
import org.saar.maths.utils.Vector3
import kotlin.random.Random

class DiversityColorGenerator(
    private val randomness: Float,
    private val generator: ColorGenerator
) : ColorGenerator {

    override fun generateColor(position: Vector3fc, normal: Vector3fc): Vector3fc {
        val color = this.generator.generateColor(position, normal)

        return Vector3.of(color).add(
            Random.nextFloat() * this.randomness,
            Random.nextFloat() * this.randomness,
            Random.nextFloat() * this.randomness,
        )
    }
}