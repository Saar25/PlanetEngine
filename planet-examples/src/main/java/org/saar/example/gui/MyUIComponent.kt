package org.saar.example.gui

import org.saar.gui.UIBlockElement
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours
import org.saar.gui.style.Style
import org.saar.gui.style.value.CoordinateValues
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.gui.style.value.LengthValues.percent

class MyUIComponent : UIComponent() {

    private val object1 = UIBlockElement()
    private val object2 = UIBlockElement()

    override val children = listOf(
        object1.also { it.parent = this },
        object2.also { it.parent = this }
    )

    init {
        style.x.set(center())
        style.y.set(50)
        style.width.set(percent(80f))
        style.height.set(percent(80f))

        fun initChildStyle(style: Style) {
            style.radiuses.set(20)
            style.borders.set(2)
            style.backgroundColour.set(Colours.CYAN)
            style.borderColour.set(Colours.LIGHT_GREY)
        }

        initChildStyle(object1.style)
        initChildStyle(object2.style)

        object1.style.width.set(percent(48f))

        object2.style.x.set(CoordinateValues.percent(52f))
        object2.style.width.set(percent(48f))
    }

    override fun onMousePress(event: MouseEvent) {
        println("Mouse pressed!")
    }

    override fun onMouseRelease(event: MouseEvent) {
        println("Mouse released!")
    }

}