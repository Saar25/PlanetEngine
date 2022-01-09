package org.saar.gui.style.value

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object LengthValues {

    @JvmStatic
    val zero = pixels(0)

    @JvmStatic
    val inherit = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) = parent.style.width.get()

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) = parent.style.height.get()

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) = 0

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) = 0
    }

    @JvmStatic
    val fill = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            parent.style.width.get() - container.style.borders.left - container.style.borders.right

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            parent.style.height.get() - container.style.borders.top - container.style.borders.bottom

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) = 0

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) = 0
    }

    @JvmStatic
    val fit = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode): Int {
            return if (container !is UIParentNode || container.children.isEmpty()) 0
            else container.children.maxOf { it.style.position.getX() + it.style.width.getMin() } - container.style.position.getX()
        }

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode): Int {
            return if (container !is UIParentNode || container.children.isEmpty()) 0
            else container.children.maxOf { it.style.position.getY() + it.style.height.getMin() } - container.style.position.getY()
        }

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) = computeAxisX(parent, container)

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) = computeAxisY(parent, container)
    }

    @JvmStatic
    fun pixels(pixels: Int) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) = pixels

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) = pixels

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) = pixels

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) = pixels
    }

    @JvmStatic
    fun percent(percents: Float) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            (parent.style.width.get() * percents / 100).toInt()

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            (parent.style.height.get() * percents / 100).toInt()

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) = 0

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) = 0
    }

    @JvmStatic
    fun ratio(ratio: Float) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            (container.style.height.get() * ratio).toInt()

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            (container.style.width.get() * ratio).toInt()

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) =
            (container.style.height.getMin() * ratio).toInt()

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) =
            (container.style.width.getMin() * ratio).toInt()
    }

    @JvmStatic
    fun add(a: LengthValue, b: LengthValue) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisX(parent, container) + b.computeAxisX(parent, container)

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisY(parent, container) + b.computeAxisY(parent, container)

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeMinAxisX(parent, container) + b.computeMinAxisX(parent, container)

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeMinAxisY(parent, container) + b.computeMinAxisY(parent, container)
    }

    @JvmStatic
    fun sub(a: LengthValue, b: LengthValue) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisX(parent, container) - b.computeAxisX(parent, container)

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisY(parent, container) - b.computeAxisY(parent, container)

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeMinAxisX(parent, container) - b.computeMinAxisX(parent, container)

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeMinAxisY(parent, container) - b.computeMinAxisY(parent, container)
    }

    @JvmStatic
    fun add(a: LengthValue, b: Int) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisX(parent, container) + b

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisY(parent, container) + b

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeMinAxisX(parent, container) + b

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeMinAxisY(parent, container) + b
    }

    @JvmStatic
    fun sub(a: LengthValue, b: Int) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisX(parent, container) - b

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisY(parent, container) - b

        override fun computeMinAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeMinAxisX(parent, container) - b

        override fun computeMinAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeMinAxisY(parent, container) - b
    }
}