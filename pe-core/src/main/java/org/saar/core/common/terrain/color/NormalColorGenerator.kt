package org.saar.core.common.terrain.color

import org.joml.Vector3fc
import org.saar.maths.utils.Maths

class NormalColorGenerator(private val normal: Vector3fc, vararg normalColors: NormalColor) : ColorGenerator {

    private val normalColors = listOf(*normalColors).sortedBy { it.product }

    override fun generateColor(position: Vector3fc, normal: Vector3fc): Vector3fc {
        val product = this.normal.dot(normal)

        if (this.normalColors.first().product >= product) {
            return this.normalColors.first().color
        }

        for (i in 0 until this.normalColors.size - 1) {
            if (this.normalColors[i].product == product) {
                return this.normalColors[i].color
            }
            if (this.normalColors[i].product < product) {
                val a = this.normalColors[i]
                val b = this.normalColors[i + 1]
                val scalar = (product - a.product) / (b.product - a.product)
                return Maths.mix(a.color, b.color, scalar)
            }
        }

        return this.normalColors.last().color
    }
}