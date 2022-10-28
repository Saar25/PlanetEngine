package org.saar.gui.style.arrangement

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

interface ArrangementValue {
    fun computeAxisX(container: UIParentNode, child: UIChildNode): Int

    fun computeAxisY(container: UIParentNode, child: UIChildNode): Int
}