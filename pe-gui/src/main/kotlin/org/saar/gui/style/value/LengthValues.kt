package org.saar.gui.style.value

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object LengthValues {

    @JvmStatic
    val zero = LengthValue.Simple { _: UIParentNode, _: UIChildNode -> 0 }

    @JvmStatic
    val inherit = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) = parent.style.width.get()
        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) = parent.style.height.get()
    }

    @JvmStatic
    val fill = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            parent.style.width.get() - container.style.borders.left - container.style.borders.right

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            parent.style.height.get() - container.style.borders.top - container.style.borders.bottom
    }

    @JvmStatic
    fun pixels(pixels: Int) = LengthValue.Simple { _: UIParentNode, _: UIChildNode -> pixels }

    @JvmStatic
    fun percent(percents: Float) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            (parent.style.width.get() * percents / 100).toInt()

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            (parent.style.height.get() * percents / 100).toInt()
    }

    @JvmStatic
    fun ratio(ratio: Float) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            (container.style.height.get() * ratio).toInt()

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            (container.style.width.get() * ratio).toInt()
    }

    @JvmStatic
    fun add(a: LengthValue, b: LengthValue) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisX(parent, container) + b.computeAxisX(parent, container)

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisY(parent, container) + b.computeAxisY(parent, container)
    }

    @JvmStatic
    fun sub(a: LengthValue, b: LengthValue) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisX(parent, container) - b.computeAxisX(parent, container)

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisY(parent, container) - b.computeAxisY(parent, container)
    }

    @JvmStatic
    fun add(a: LengthValue, b: Int) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisX(parent, container) + b

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisY(parent, container) + b
    }

    @JvmStatic
    fun sub(a: LengthValue, b: Int) = object : LengthValue {
        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisX(parent, container) - b

        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) =
            a.computeAxisY(parent, container) - b
    }
}