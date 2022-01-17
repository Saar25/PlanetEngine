package org.saar.gui.style.fontsize

import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.UIChildNode

fun interface FontSizeValue {

    fun build(container: UIChildNode): ObservableIntegerValue

}