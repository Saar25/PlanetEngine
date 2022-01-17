package org.saar.gui.style.bordercolour

import org.jproperty.ObservableValue
import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

fun interface BorderColourValue {

    fun build(container: UIChildNode): ObservableValue<Colour>

}