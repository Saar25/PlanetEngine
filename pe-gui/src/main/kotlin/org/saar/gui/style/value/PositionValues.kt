package org.saar.gui.style.value

import org.saar.gui.UIChildElement

object PositionValues {

    @JvmStatic
    fun relative() = object : PositionValue {
        override fun computeAxisX(container: UIChildElement): Int {
            return container.parent.style.alignment.getX(container)
        }

        override fun computeAxisY(container: UIChildElement): Int {
            return container.parent.style.alignment.getY(container)
        }
    }

    @JvmStatic
    fun absolute() = object : PositionValue {
        override fun computeAxisX(container: UIChildElement): Int {
            return container.style.x.get()
        }

        override fun computeAxisY(container: UIChildElement): Int {
            return container.style.y.get()
        }
    }

}