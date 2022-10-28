package org.saar.gui.style.axisalignment

import org.saar.gui.UIChildNode
import org.saar.gui.style.StyleProperty

interface ReadonlyAxisAlignment : StyleProperty {

    val value: AxisAlignmentValue

    fun getX(child: UIChildNode): Int

    fun getY(child: UIChildNode): Int

}