package org.saar.gui.style.alignment

import org.saar.gui.UIChildNode
import org.saar.gui.style.StyleProperty

interface ReadonlyAlignment : StyleProperty {

    val value: AlignmentValue

    fun getX(child: UIChildNode): Int

    fun getY(child: UIChildNode): Int

}