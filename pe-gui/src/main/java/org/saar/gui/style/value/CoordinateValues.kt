package org.saar.gui.style.value

import org.saar.gui.style.IStyle

object CoordinateValues {

    @JvmStatic
    val zero = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle) = parent.x.get()
        override fun computeAxisY(parent: IStyle, style: IStyle) = parent.y.get()
    }

    @JvmStatic
    fun pixels(pixels: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle) = parent.x.get() + pixels
        override fun computeAxisY(parent: IStyle, style: IStyle) = parent.y.get() + pixels
    }

    @JvmStatic
    fun percent(percents: Float) = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle): Int {
            return parent.x.get() + (parent.width.get() * percents / 100).toInt()
        }

        override fun computeAxisY(parent: IStyle, style: IStyle): Int {
            return parent.y.get() + (parent.height.get() * percents / 100).toInt()
        }
    }

    @JvmStatic
    fun center() = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle): Int {
            return parent.x.get() + (parent.width.get() - style.width.get()) / 2
        }

        override fun computeAxisY(parent: IStyle, style: IStyle): Int {
            return parent.y.get() + (parent.height.get() - style.height.get()) / 2
        }
    }

    @JvmStatic
    fun pixelsCenter(pixels: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle): Int {
            return parent.x.get() + pixels - style.width.get() / 2
        }

        override fun computeAxisY(parent: IStyle, style: IStyle): Int {
            return parent.y.get() + pixels - style.height.get() / 2
        }
    }

    @JvmStatic
    fun percentCenter(percents: Float) = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle): Int {
            return parent.x.get() + (parent.width.get() * percents / 100).toInt() - style.width.get() / 2
        }

        override fun computeAxisY(parent: IStyle, style: IStyle): Int {
            return parent.y.get() + (parent.height.get() * percents / 100).toInt() - style.height.get() / 2
        }
    }

    @JvmStatic
    fun add(a: CoordinateValue, b: CoordinateValue) = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle): Int {
            return a.computeAxisX(parent, style) + b.computeAxisX(parent, style)
        }

        override fun computeAxisY(parent: IStyle, style: IStyle): Int {
            return a.computeAxisY(parent, style) + b.computeAxisY(parent, style)
        }
    }

    @JvmStatic
    fun sub(a: CoordinateValue, b: CoordinateValue) = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle): Int {
            return a.computeAxisX(parent, style) - b.computeAxisX(parent, style)
        }

        override fun computeAxisY(parent: IStyle, style: IStyle): Int {
            return a.computeAxisY(parent, style) - b.computeAxisY(parent, style)
        }
    }

    @JvmStatic
    fun add(a: CoordinateValue, b: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle): Int {
            return a.computeAxisX(parent, style) + b
        }

        override fun computeAxisY(parent: IStyle, style: IStyle): Int {
            return a.computeAxisY(parent, style) + b
        }
    }

    @JvmStatic
    fun sub(a: CoordinateValue, b: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: IStyle, style: IStyle): Int {
            return a.computeAxisX(parent, style) - b
        }

        override fun computeAxisY(parent: IStyle, style: IStyle): Int {
            return a.computeAxisY(parent, style) - b
        }
    }
}