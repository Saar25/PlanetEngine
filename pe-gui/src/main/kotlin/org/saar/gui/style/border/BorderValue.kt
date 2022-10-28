package org.saar.gui.style.border

import org.saar.gui.UIChildNode

interface BorderValue {
    fun computeTop(container: UIChildNode): Int

    fun computeRight(container: UIChildNode): Int

    fun computeBottom(container: UIChildNode): Int

    fun computeLeft(container: UIChildNode): Int
}