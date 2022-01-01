package org.saar.gui.style.value

import org.saar.gui.style.ContainerStyle
import org.saar.gui.style.IStyle

interface AxisAlignmentValue {
    fun computeAxisX(parent: ContainerStyle, child: IStyle): Int

    fun computeAxisY(parent: ContainerStyle, child: IStyle): Int
}