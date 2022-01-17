package org.saar.gui.style.fontcolour

import org.jproperty.ObservableValue
import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

fun interface FontColourValue {

    fun build(container: UIChildNode): ObservableValue<Colour>

}