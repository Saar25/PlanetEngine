package org.saar.gui.style.length

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object LengthValues {

    @JvmStatic
    val zero = pixels(0)

    @JvmStatic
    val inherit = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = container.parent.style.width.getMax()

        override fun computeAxisY(container: UIChildNode) = container.parent.style.height.getMax()

        override fun computeMinAxisX(container: UIChildNode) = 0

        override fun computeMinAxisY(container: UIChildNode) = 0

        override fun computeMaxAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMaxAxisY(container: UIChildNode) = computeAxisY(container)
    }

    @JvmStatic
    val fill = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) =
            container.parent.style.width.get() -
                    container.style.borders.left - container.style.borders.right -
                    container.style.margin.left - container.style.margin.right

        override fun computeAxisY(container: UIChildNode) =
            container.parent.style.height.get() -
                    container.style.borders.top - container.style.borders.bottom -
                    container.style.margin.top - container.style.margin.bottom

        override fun computeMinAxisX(container: UIChildNode) = 0

        override fun computeMinAxisY(container: UIChildNode) = 0

        override fun computeMaxAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMaxAxisY(container: UIChildNode) = computeAxisY(container)
    }

    @JvmStatic
    val fit = object : LengthValue {
        override fun computeAxisX(container: UIChildNode): Int {
            return if (container !is UIParentNode || container.children.isEmpty()) 0
            else container.children.maxOf { it.style.position.getX() + it.style.width.getMin() } - container.style.position.getX()
        }

        override fun computeAxisY(container: UIChildNode): Int {
            return if (container !is UIParentNode || container.children.isEmpty()) 0
            else container.children.maxOf { it.style.position.getY() + it.style.height.getMin() } - container.style.position.getY()
        }

        override fun computeMinAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMinAxisY(container: UIChildNode) = computeAxisY(container)

        override fun computeMaxAxisX(container: UIChildNode) = fill.computeMaxAxisX(container)

        override fun computeMaxAxisY(container: UIChildNode) = fill.computeMaxAxisY(container)
    }

    @JvmStatic
    fun pixels(pixels: Int) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = pixels

        override fun computeAxisY(container: UIChildNode) = pixels

        override fun computeMinAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMinAxisY(container: UIChildNode) = computeAxisY(container)

        override fun computeMaxAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMaxAxisY(container: UIChildNode) = computeAxisY(container)
    }

    @JvmStatic
    fun percent(percents: Float) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) =
            (container.parent.style.width.getMax() * percents / 100).toInt()

        override fun computeAxisY(container: UIChildNode) =
            (container.parent.style.height.getMax() * percents / 100).toInt()

        override fun computeMinAxisX(container: UIChildNode) = 0

        override fun computeMinAxisY(container: UIChildNode) = 0

        override fun computeMaxAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMaxAxisY(container: UIChildNode) = computeAxisY(container)
    }

    @JvmStatic
    fun ratio(ratio: Float) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = (container.style.height.get() * ratio).toInt()

        override fun computeAxisY(container: UIChildNode) = (container.style.width.get() * ratio).toInt()

        override fun computeMinAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMinAxisY(container: UIChildNode) = computeAxisY(container)

        override fun computeMaxAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMaxAxisY(container: UIChildNode) = computeAxisY(container)
    }
}