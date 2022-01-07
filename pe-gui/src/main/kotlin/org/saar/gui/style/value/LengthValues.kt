package org.saar.gui.style.value

import org.saar.gui.style.Style

object LengthValues {

    @JvmStatic
    val zero = LengthValue.Simple { _: Style, _: Style -> 0 }

    @JvmStatic
    val inherit = object : LengthValue {
        override fun computeAxisX(parent: Style, style: Style) = parent.width.get()
        override fun computeAxisY(parent: Style, style: Style) = parent.height.get()
    }

    @JvmStatic
    fun pixels(pixels: Int) = LengthValue.Simple { _: Style, _: Style -> pixels }

    @JvmStatic
    fun percent(percents: Float) = object : LengthValue {
        override fun computeAxisX(parent: Style, style: Style) = (parent.width.get() * percents / 100).toInt()
        override fun computeAxisY(parent: Style, style: Style) = (parent.height.get() * percents / 100).toInt()
    }

    @JvmStatic
    fun ratio(ratio: Float) = object : LengthValue {
        override fun computeAxisX(parent: Style, style: Style) = (style.height.get() * ratio).toInt()
        override fun computeAxisY(parent: Style, style: Style) = (style.width.get() * ratio).toInt()
    }

    @JvmStatic
    fun add(a: LengthValue, b: LengthValue) = object : LengthValue {
        override fun computeAxisX(parent: Style, style: Style) =
            a.computeAxisX(parent, style) + b.computeAxisX(parent, style)

        override fun computeAxisY(parent: Style, style: Style) =
            a.computeAxisY(parent, style) + b.computeAxisY(parent, style)
    }

    @JvmStatic
    fun sub(a: LengthValue, b: LengthValue) = object : LengthValue {
        override fun computeAxisX(parent: Style, style: Style) =
            a.computeAxisX(parent, style) - b.computeAxisX(parent, style)

        override fun computeAxisY(parent: Style, style: Style) =
            a.computeAxisY(parent, style) - b.computeAxisY(parent, style)
    }

    @JvmStatic
    fun add(a: LengthValue, b: Int) = object : LengthValue {
        override fun computeAxisX(parent: Style, style: Style) =
            a.computeAxisX(parent, style) + b

        override fun computeAxisY(parent: Style, style: Style) =
            a.computeAxisY(parent, style) + b
    }

    @JvmStatic
    fun sub(a: LengthValue, b: Int) = object : LengthValue {
        override fun computeAxisX(parent: Style, style: Style) =
            a.computeAxisX(parent, style) - b

        override fun computeAxisY(parent: Style, style: Style) =
            a.computeAxisY(parent, style) - b
    }
}