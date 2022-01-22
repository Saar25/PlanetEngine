package org.saar.gui.style.alignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

interface AlignmentValue {
    fun computeAxisX(container: UIParentNode, child: UIChildNode): Int

    fun computeAxisY(container: UIParentNode, child: UIChildNode): Int
}