package org.saar.gui.style.value

import org.saar.gui.UINode

interface CoordinateValue {
    fun computeAxisX(parent: UINode, child: UINode): Int

    fun computeAxisY(parent: UINode, child: UINode): Int
}