package org.saar.gui.style.redius

import org.saar.gui.UIChildNode

interface RadiusValue {
    fun computeTopRight(container: UIChildNode): Int

    fun computeTopLeft(container: UIChildNode): Int

    fun computeBottomRight(container: UIChildNode): Int

    fun computeBottomLeft(container: UIChildNode): Int
}