package org.saar.gui.style.margin

import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.UIChildNode

interface StyleMarginValue {
    fun buildTop(container: UIChildNode): ObservableIntegerValue
    fun buildRight(container: UIChildNode): ObservableIntegerValue
    fun buildBottom(container: UIChildNode): ObservableIntegerValue
    fun buildLeft(container: UIChildNode): ObservableIntegerValue
}