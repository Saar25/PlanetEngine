package org.saar.gui.style.coordinate

import org.saar.gui.UIChildNode

object CoordinateValues {

    @JvmStatic
    val zero = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) = 0

        override fun computeAxisY(container: UIChildNode) = 0
    }

    @JvmStatic
    fun pixels(pixels: Int) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) = pixels

        override fun computeAxisY(container: UIChildNode) = pixels
    }

    @JvmStatic
    fun percent(percents: Float) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) =
            (container.parent.style.width.get() * percents / 100).toInt()

        override fun computeAxisY(container: UIChildNode) =
            (container.parent.style.height.get() * percents / 100).toInt()
    }

    @JvmStatic
    fun center() = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) =
            (container.parent.style.width.get() - container.style.width.get()) / 2

        override fun computeAxisY(container: UIChildNode) =
            (container.parent.style.height.get() - container.style.height.get()) / 2
    }

    @JvmStatic
    fun pixelsCenter(pixels: Int) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) = pixels - container.style.width.get() / 2

        override fun computeAxisY(container: UIChildNode) = pixels - container.style.height.get() / 2
    }

    @JvmStatic
    fun percentCenter(percents: Float) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) =
            (container.parent.style.width.get() * percents / 100).toInt() - container.style.width.get() / 2

        override fun computeAxisY(container: UIChildNode) =
            (container.parent.style.height.get() * percents / 100).toInt() - container.style.height.get() / 2
    }

    @JvmStatic
    fun pixelsEnd(pixels: Int) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) =
            container.parent.style.width.get() - pixels - container.style.width.get()

        override fun computeAxisY(container: UIChildNode) =
            container.parent.style.height.get() - pixels - container.style.height.get()
    }

    @JvmStatic
    fun percentEnd(percents: Float) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) =
            (container.parent.style.width.get() * percents / 100).toInt() - container.style.width.get()

        override fun computeAxisY(container: UIChildNode) =
            (container.parent.style.height.get() * percents / 100).toInt() - container.style.height.get()
    }

    @JvmStatic
    fun add(a: CoordinateValue, b: CoordinateValue) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) = a.computeAxisX(container) + b.computeAxisX(container)

        override fun computeAxisY(container: UIChildNode) = a.computeAxisY(container) + b.computeAxisY(container)
    }

    @JvmStatic
    fun sub(a: CoordinateValue, b: CoordinateValue) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) = a.computeAxisX(container) - b.computeAxisX(container)

        override fun computeAxisY(container: UIChildNode) = a.computeAxisY(container) - b.computeAxisY(container)
    }

    @JvmStatic
    fun add(a: CoordinateValue, b: Int) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) = a.computeAxisX(container) + b

        override fun computeAxisY(container: UIChildNode) = a.computeAxisY(container) + b
    }

    @JvmStatic
    fun sub(a: CoordinateValue, b: Int) = object : CoordinateValue {
        override fun computeAxisX(container: UIChildNode) = a.computeAxisX(container) - b

        override fun computeAxisY(container: UIChildNode) = a.computeAxisY(container) - b
    }
}