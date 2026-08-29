package org.saar.core.common.terrain.color

import org.joml.Vector3fc
import org.saar.maths.utils.Maths
import org.saar.maths.utils.Vector3

class HeightColorGenerator(vararg heightColors: HeightColor) : ColorGenerator {

    private val heightColors = listOf(*heightColors).sortedBy { it.y }

    override fun generateColor(position: Vector3fc, normal: Vector3fc): Vector3fc {
        if (position.y() <= this.heightColors.first().y) {
            return this.heightColors.first().color
        }
        if (position.y() >= this.heightColors.last().y) {
            return this.heightColors.last().color
        }

        for (i in 0 until this.heightColors.size - 1) {
            if (this.heightColors[i].y == position.y()) {
                return this.heightColors[i].color
            }
            if (this.heightColors[i].y < position.y()) {
                val a = this.heightColors[i]
                val b = this.heightColors[i + 1]
                val scalar = (position.y() - a.y) / (b.y - a.y)
                return Maths.mix(a.color, b.color, scalar)
            }
        }

        // Unreachable code
        return Vector3.ZERO
    }
}