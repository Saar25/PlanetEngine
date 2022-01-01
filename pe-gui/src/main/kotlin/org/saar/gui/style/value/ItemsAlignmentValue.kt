package org.saar.gui.style.value

import org.saar.gui.UIChildElement
import org.saar.gui.UIContainer

interface ItemsAlignmentValue {
    fun computeAxisX(parent: UIContainer, child: UIChildElement): Int

    fun computeAxisY(parent: UIContainer, child: UIChildElement): Int
}