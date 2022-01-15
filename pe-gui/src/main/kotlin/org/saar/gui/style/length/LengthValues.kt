package org.saar.gui.style.length

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object LengthValues {

    @JvmStatic
    val zero = pixels(0)

    @JvmStatic
    val inherit = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = container.parent.style.width.get()

        override fun computeAxisY(container: UIChildNode) = container.parent.style.height.get()

        override fun computeMinAxisX(container: UIChildNode) = 0

        override fun computeMinAxisY(container: UIChildNode) = 0
    }

    @JvmStatic
    val fill = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) =
            container.parent.style.width.get() - container.style.borders.left.get() - container.style.borders.right.get()

        override fun computeAxisY(container: UIChildNode) =
            container.parent.style.height.get() - container.style.borders.top.get() - container.style.borders.bottom.get()

        override fun computeMinAxisX(container: UIChildNode) = 0

        override fun computeMinAxisY(container: UIChildNode) = 0
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
    }

    @JvmStatic
    fun pixels(pixels: Int) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = pixels

        override fun computeAxisY(container: UIChildNode) = pixels

        override fun computeMinAxisX(container: UIChildNode) = pixels

        override fun computeMinAxisY(container: UIChildNode) = pixels
    }

    @JvmStatic
    fun percent(percents: Float) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) =
            (container.parent.style.width.get() * percents / 100).toInt()

        override fun computeAxisY(container: UIChildNode) =
            (container.parent.style.height.get() * percents / 100).toInt()

        override fun computeMinAxisX(container: UIChildNode) = 0

        override fun computeMinAxisY(container: UIChildNode) = 0
    }

    @JvmStatic
    fun ratio(ratio: Float) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = (container.style.height.get() * ratio).toInt()

        override fun computeAxisY(container: UIChildNode) = (container.style.width.get() * ratio).toInt()

        override fun computeMinAxisX(container: UIChildNode) = (container.style.height.getMin() * ratio).toInt()

        override fun computeMinAxisY(container: UIChildNode) = (container.style.width.getMin() * ratio).toInt()
    }

    @JvmStatic
    fun add(a: LengthValue, b: LengthValue) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) =
            a.computeAxisX(container) + b.computeAxisX(container)

        override fun computeAxisY(container: UIChildNode) =
            a.computeAxisY(container) + b.computeAxisY(container)

        override fun computeMinAxisX(container: UIChildNode) =
            a.computeMinAxisX(container) + b.computeMinAxisX(container)

        override fun computeMinAxisY(container: UIChildNode) =
            a.computeMinAxisY(container) + b.computeMinAxisY(container)
    }

    @JvmStatic
    fun sub(a: LengthValue, b: LengthValue) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) =
            a.computeAxisX(container) - b.computeAxisX(container)

        override fun computeAxisY(container: UIChildNode) =
            a.computeAxisY(container) - b.computeAxisY(container)

        override fun computeMinAxisX(container: UIChildNode) =
            a.computeMinAxisX(container) - b.computeMinAxisX(container)

        override fun computeMinAxisY(container: UIChildNode) =
            a.computeMinAxisY(container) - b.computeMinAxisY(container)
    }

    @JvmStatic
    fun add(a: LengthValue, b: Int) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = a.computeAxisX(container) + b

        override fun computeAxisY(container: UIChildNode) = a.computeAxisY(container) + b

        override fun computeMinAxisX(container: UIChildNode) = a.computeMinAxisX(container) + b

        override fun computeMinAxisY(container: UIChildNode) = a.computeMinAxisY(container) + b
    }

    @JvmStatic
    fun sub(a: LengthValue, b: Int) = object : LengthValue {
        override fun computeAxisX(container: UIChildNode) = a.computeAxisX(container) - b

        override fun computeAxisY(container: UIChildNode) = a.computeAxisY(container) - b

        override fun computeMinAxisX(container: UIChildNode) = a.computeMinAxisX(container) - b

        override fun computeMinAxisY(container: UIChildNode) = a.computeMinAxisY(container) - b
    }
}