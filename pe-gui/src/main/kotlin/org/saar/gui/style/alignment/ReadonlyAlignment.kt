package org.saar.gui.style.alignment

import org.saar.gui.UIChildNode
import org.saar.gui.style.StyleProperty
import org.saar.gui.style.value.AlignmentValue
import org.saar.gui.style.value.AlignmentValues

interface ReadonlyAlignment : StyleProperty {

    val value: AlignmentValue

    fun getX(child: UIChildNode): Int

    fun getY(child: UIChildNode): Int

}