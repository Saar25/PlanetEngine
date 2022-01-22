package org.saar.gui.style.position

import org.saar.gui.UIChildNode

interface PositionValue {
    fun computeAxisX(container: UIChildNode): Int

    fun computeAxisY(container: UIChildNode): Int
}