package org.saar.gui.style.value

import org.saar.gui.UIChildElement

interface PositionValue {
    fun computeAxisX(container: UIChildElement): Int

    fun computeAxisY(container: UIChildElement): Int
}