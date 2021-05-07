package org.saar.core.common.terrain.colour

import org.joml.Vector3fc
import org.saar.maths.utils.Maths
import org.saar.maths.utils.Vector3

class HeightColourGenerator(vararg heightColors: HeightColour) : ColourGenerator {

    private val heightColors = listOf(*heightColors).sortedBy { it.y }

    override fun generateColour(position: Vector3fc, normal: Vector3fc): Vector3fc {
        if (position.y() <= this.heightColors.first().y) {
            return this.heightColors.first().colour
        }
        if (position.y() >= this.heightColors.last().y) {
            return this.heightColors.last().colour
        }

        for (i in 0 until this.heightColors.size - 1) {
            if (this.heightColors[i].y == position.y()) {
                return this.heightColors[i].colour
            }
            if (this.heightColors[i].y < position.y()) {
                val a = this.heightColors[i]
                val b = this.heightColors[i + 1]
                val scalar = (position.y() - a.y) / (b.y - a.y)
                return Maths.mix(a.colour, b.colour, scalar)
            }
        }

        // Unreachable code
        return Vector3.ZERO

    }
}