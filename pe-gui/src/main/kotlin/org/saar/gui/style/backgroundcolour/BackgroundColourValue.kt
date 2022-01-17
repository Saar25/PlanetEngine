package org.saar.gui.style.backgroundcolour

import org.jproperty.ObservableValue
import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

interface BackgroundColourValue {

    fun buildTopRight(container: UIChildNode): ObservableValue<Colour>

    fun buildTopLeft(container: UIChildNode): ObservableValue<Colour>

    fun buildBottomRight(container: UIChildNode): ObservableValue<Colour>

    fun buildBottomLeft(container: UIChildNode): ObservableValue<Colour>

}