package org.saar.gui.style.value

import org.saar.gui.UIChildElement
import org.saar.gui.UIParentElement

object CoordinateValues {

    @JvmStatic
    val zero = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement) = 0
        override fun computeAxisY(parent: UIParentElement, child: UIChildElement) = 0
    }

    @JvmStatic
    fun pixels(pixels: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement) = pixels

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement) = pixels
    }

    @JvmStatic
    fun percent(percents: Float) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int {
            return (parent.style.width.get() * percents / 100).toInt()
        }

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int {
            return (parent.style.height.get() * percents / 100).toInt()
        }
    }

    @JvmStatic
    fun center() = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int {
            return (parent.style.width.get() - child.style.width.get()) / 2
        }

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int {
            return (parent.style.height.get() - child.style.height.get()) / 2
        }
    }

    @JvmStatic
    fun pixelsCenter(pixels: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int {
            return pixels - child.style.width.get() / 2
        }

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int {
            return pixels - child.style.height.get() / 2
        }
    }

    @JvmStatic
    fun percentCenter(percents: Float) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int {
            return (parent.style.width.get() * percents / 100).toInt() - child.style.width.get() / 2
        }

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int {
            return (parent.style.height.get() * percents / 100).toInt() - child.style.height.get() / 2
        }
    }

    @JvmStatic
    fun pixelsEnd(pixels: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int {
            return parent.style.width.get() - pixels - child.style.width.get()
        }

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int {
            return parent.style.height.get() - pixels - child.style.height.get()
        }
    }

    @JvmStatic
    fun percentEnd(percents: Float) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int {
            return (parent.style.width.get() * percents / 100).toInt() - child.style.width.get()
        }

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int {
            return (parent.style.height.get() * percents / 100).toInt() - child.style.height.get()
        }
    }

    @JvmStatic
    fun add(a: CoordinateValue, b: CoordinateValue) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int {
            return a.computeAxisX(parent, child) + b.computeAxisX(parent, child)
        }

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int {
            return a.computeAxisY(parent, child) + b.computeAxisY(parent, child)
        }
    }

    @JvmStatic
    fun sub(a: CoordinateValue, b: CoordinateValue) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int {
            return a.computeAxisX(parent, child) - b.computeAxisX(parent, child)
        }

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int {
            return a.computeAxisY(parent, child) - b.computeAxisY(parent, child)
        }
    }

    @JvmStatic
    fun add(a: CoordinateValue, b: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement) = a.computeAxisX(parent, child) + b

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement) = a.computeAxisY(parent, child) + b
    }

    @JvmStatic
    fun sub(a: CoordinateValue, b: Int) = object : CoordinateValue {
        override fun computeAxisX(parent: UIParentElement, child: UIChildElement) = a.computeAxisX(parent, child) - b

        override fun computeAxisY(parent: UIParentElement, child: UIChildElement) = a.computeAxisY(parent, child) - b
    }
}