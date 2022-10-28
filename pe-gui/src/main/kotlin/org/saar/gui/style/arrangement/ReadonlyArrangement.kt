package org.saar.gui.style.arrangement

import org.saar.gui.UIChildNode
import org.saar.gui.style.StyleProperty

interface ReadonlyArrangement : StyleProperty {

    val value: ArrangementValue

    fun getX(child: UIChildNode): Int

    fun getY(child: UIChildNode): Int

}