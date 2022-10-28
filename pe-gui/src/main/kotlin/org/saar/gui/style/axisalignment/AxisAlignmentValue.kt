package org.saar.gui.style.axisalignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

interface AxisAlignmentValue {
    fun computeAxisX(container: UIParentNode, child: UIChildNode): Int

    fun computeAxisY(container: UIParentNode, child: UIChildNode): Int
}