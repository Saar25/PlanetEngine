package org.saar.core.common.terrain.colour

import org.joml.Vector3fc
import org.saar.maths.utils.Vector3
import kotlin.random.Random

class AlterColourGenerator(private val max: Float, private val generator: ColourGenerator) : ColourGenerator {

    override fun generateColour(position: Vector3fc, normal: Vector3fc): Vector3fc {
        val colour = this.generator.generateColour(position, normal)
        return Vector3.of(colour).add(
            Random.nextFloat() * this.max,
            Random.nextFloat() * this.max,
            Random.nextFloat() * this.max,
        )
    }
}