package org.saar.gui.style.backgroundcolour

import org.jproperty.ObservableValue
import org.jproperty.constant.ConstantObjectProperty
import org.jproperty.map
import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

object BackgroundColourValues {

    @JvmStatic
    val inherit: BackgroundColourValue = object : BackgroundColourValue {
        override fun buildTopRight(container: UIChildNode): ObservableValue<Colour> =
            container.parentProperty.map { it.style.backgroundColour.topRight.value }

        override fun buildTopLeft(container: UIChildNode): ObservableValue<Colour> =
            container.parentProperty.map { it.style.backgroundColour.topLeft.value }

        override fun buildBottomRight(container: UIChildNode): ObservableValue<Colour> =
            container.parentProperty.map { it.style.backgroundColour.bottomRight.value }

        override fun buildBottomLeft(container: UIChildNode): ObservableValue<Colour> =
            container.parentProperty.map { it.style.backgroundColour.bottomLeft.value }
    }

    @JvmStatic
    fun of(value: Colour): BackgroundColourValue = object : BackgroundColourValue {
        override fun buildTopRight(container: UIChildNode) = ConstantObjectProperty(value)

        override fun buildTopLeft(container: UIChildNode) = ConstantObjectProperty(value)

        override fun buildBottomRight(container: UIChildNode) = ConstantObjectProperty(value)

        override fun buildBottomLeft(container: UIChildNode) = ConstantObjectProperty(value)
    }

}