package org.saar.gui.style.coordinate

import org.saar.gui.UIChildNode

interface CoordinateValue {
    fun computeAxisX(container: UIChildNode): Int

    fun computeAxisY(container: UIChildNode): Int
}