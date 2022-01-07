package org.saar.gui.style.value

import org.saar.gui.UIChildElement
import org.saar.gui.UIParentElement

interface AlignmentValue {
    fun computeAxisX(parent: UIParentElement, child: UIChildElement): Int

    fun computeAxisY(parent: UIParentElement, child: UIChildElement): Int
}