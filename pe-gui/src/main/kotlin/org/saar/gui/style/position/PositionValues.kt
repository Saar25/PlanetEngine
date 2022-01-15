package org.saar.gui.style.position

import org.saar.gui.UIChildNode

object PositionValues {

    @JvmStatic
    val relative = object : PositionValue {
        override fun computeAxisX(container: UIChildNode): Int {
            return container.parent.style.position.getX() +
                    container.parent.style.alignment.getX(container) +
                    container.style.borders.left.get() +
                    container.style.margin.left.get()
        }

        override fun computeAxisY(container: UIChildNode): Int {
            return container.parent.style.position.getY() +
                    container.parent.style.alignment.getY(container) +
                    container.style.borders.top.get() +
                    container.style.margin.top.get()
        }
    }

    @JvmStatic
    val absolute = object : PositionValue {
        override fun computeAxisX(container: UIChildNode): Int {
            return container.parent.style.position.getX() + container.style.x.get()
        }

        override fun computeAxisY(container: UIChildNode): Int {
            return container.parent.style.position.getY() + container.style.y.get()
        }
    }

}