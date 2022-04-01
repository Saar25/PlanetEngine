package org.saar.gui.style.length

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode
import org.saar.gui.style.alignment.AlignmentValues

object LengthValues {

    @JvmField
    val zero = pixels(0)

    @JvmField
    val inherit = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = container.parent.style.width.getMax()

        override fun computeAxisY(container: UIChildNode) = container.parent.style.height.getMax()

        override fun computeMinAxisX(container: UIChildNode) = 0

        override fun computeMinAxisY(container: UIChildNode) = 0

        override fun computeMaxAxisX(container: UIChildNode) = computeAxisX(container)

        override fun computeMaxAxisY(container: UIChildNode) = computeAxisY(container)
    }

    @JvmField
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

    @JvmField
    val fit = object : LengthValue {
        override fun computeAxisX(container: UIChildNode): Int {
            return if (container !is UIParentNode || container.children.isEmpty()) 0
            else if (container.style.alignment.value == AlignmentValues.horizontal)
                container.children.sumOf {
                    it.style.width.getMin() +
                            it.style.borders.left + it.style.borders.right +
                            it.style.margin.left + it.style.margin.right
                }
            else if (container.style.alignment.value == AlignmentValues.vertical)
                container.children.maxOf {
                    it.style.width.getMin() +
                            it.style.borders.left + it.style.borders.right +
                            it.style.margin.left + it.style.margin.right
                }
            else 0

        }

        override fun computeAxisY(container: UIChildNode): Int {
            return if (container !is UIParentNode || container.children.isEmpty()) 0
            else if (container.style.alignment.value == AlignmentValues.vertical)
                container.children.sumOf {
                    it.style.height.getMin() +
                            it.style.borders.top + it.style.borders.bottom +
                            it.style.margin.top + it.style.margin.bottom
                }
            else if (container.style.alignment.value == AlignmentValues.horizontal)
                container.children.maxOf {
                    it.style.height.getMin() +
                            it.style.borders.top + it.style.borders.bottom +
                            it.style.margin.top + it.style.margin.bottom
                }
            else 0
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