package org.saar.gui.style.border

import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.UIChildNode

interface StyleBorderValue {
    fun buildTop(container: UIChildNode): ObservableIntegerValue

    fun buildRight(container: UIChildNode): ObservableIntegerValue

    fun buildBottom(container: UIChildNode): ObservableIntegerValue

    fun buildLeft(container: UIChildNode): ObservableIntegerValue
}