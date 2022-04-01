package org.saar.gui.style.position

import org.saar.gui.UIChildNode

object PositionValues {

    @JvmField
    val relative = object : PositionValue {
        override fun computeAxisX(container: UIChildNode): Int {
            return container.parent.style.position.getX() +
                    container.style.borders.left +
                    container.style.margin.left +
                    container.parent.style.alignment.getX(container)
        }

        override fun computeAxisY(container: UIChildNode): Int {
            return container.parent.style.position.getY() +
                    container.style.borders.top +
                    container.style.margin.top +
                    container.parent.style.alignment.getY(container)
        }
    }

    @JvmField
    val absolute = object : PositionValue {
        override fun computeAxisX(container: UIChildNode): Int {
            return container.parent.style.position.getX() +
                    container.style.borders.top +
                    container.style.x.get()
        }

        override fun computeAxisY(container: UIChildNode): Int {
            return container.parent.style.position.getY() +
                    container.style.borders.left +
                    container.style.y.get()
        }
    }
}