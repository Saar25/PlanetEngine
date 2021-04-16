package org.saar.example.gui

import org.saar.gui.UIBlock
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.position.coordinate.CoordinateValues
import org.saar.gui.position.coordinate.CoordinateValues.center
import org.saar.gui.position.length.LengthValues.percent
import org.saar.gui.style.Colours

class MyUIComponent : UIComponent() {

    init {
        this.positioner.x.set(center())
        this.positioner.y.set(center())
        this.positioner.width.set(percent(80f))
        this.positioner.height.set(percent(80f))

        val object1 = UIBlock()
        add(object1)

        object1.positioner.width.set(percent(48f))
        object1.style.backgroundColour.set(Colours.CYAN)
        object1.style.borderColour.set(Colours.LIGHT_GREY)
        object1.style.borders.set(2)
        object1.style.radiuses.set(100)

        val object2 = UIBlock()
        add(object2)

        object2.positioner.x.set(CoordinateValues.percent(52f))
        object2.positioner.width.set(percent(48f))
        object2.style.backgroundColour.set(Colours.CYAN)
        object2.style.borderColour.set(Colours.LIGHT_GREY)
        object2.style.borders.set(2)
        object2.style.radiuses.set(100)
    }

    override fun onMousePress(event: MouseEvent) {
        println("Mouse pressed!")
    }

    override fun onMouseRelease(event: MouseEvent) {
        println("Mouse released!")
    }

}