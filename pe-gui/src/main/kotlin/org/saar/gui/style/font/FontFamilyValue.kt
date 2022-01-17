package org.saar.gui.style.font

import org.jproperty.ObservableValue
import org.saar.gui.UIChildNode
import org.saar.gui.font.Font

fun interface FontFamilyValue {

    fun build(container: UIChildNode): ObservableValue<Font>

}