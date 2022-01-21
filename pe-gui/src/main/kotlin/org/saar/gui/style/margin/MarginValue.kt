package org.saar.gui.style.margin

import org.saar.gui.UIChildNode

interface MarginValue {
    fun computeTop(container: UIChildNode): Int

    fun computeRight(container: UIChildNode): Int

    fun computeBottom(container: UIChildNode): Int

    fun computeLeft(container: UIChildNode): Int
}