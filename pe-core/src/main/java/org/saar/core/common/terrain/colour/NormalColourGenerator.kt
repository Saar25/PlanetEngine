package org.saar.core.common.terrain.colour

import org.joml.Vector3fc
import org.saar.maths.utils.Maths

class NormalColourGenerator(private val normal: Vector3fc, vararg normalColours: NormalColour) : ColourGenerator {

    private val normalColours = listOf(*normalColours).sortedBy { it.product }

    override fun generateColour(position: Vector3fc, normal: Vector3fc): Vector3fc {
        val product = this.normal.dot(normal)

        if (this.normalColours.first().product >= product) {
            return this.normalColours.first().colour
        }

        for (i in 0 until this.normalColours.size - 1) {
            if (this.normalColours[i].product == product) {
                return this.normalColours[i].colour
            }
            if (this.normalColours[i].product < product) {
                val a = this.normalColours[i]
                val b = this.normalColours[i + 1]
                val scalar = (product - a.product) / (b.product - a.product)
                return Maths.mix(a.colour, b.colour, scalar)
            }
        }

        return this.normalColours.last().colour
    }
}