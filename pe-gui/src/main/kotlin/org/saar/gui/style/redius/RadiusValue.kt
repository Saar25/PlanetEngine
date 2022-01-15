package org.saar.gui.style.redius

import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.UIChildNode

interface RadiusValue {
    fun buildTopRight(container: UIChildNode): ObservableIntegerValue

    fun buildTopLeft(container: UIChildNode): ObservableIntegerValue

    fun buildBottomRight(container: UIChildNode): ObservableIntegerValue

    fun buildBottomLeft(container: UIChildNode): ObservableIntegerValue
}