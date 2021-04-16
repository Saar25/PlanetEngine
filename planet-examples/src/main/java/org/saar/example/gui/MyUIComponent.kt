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

        this.style.radiuses.set(100)
        this.style.borders.set(2)
        this.style.backgroundColour.set(Colours.CYAN)
        this.style.borderColour.set(Colours.LIGHT_GREY)

        val object1 = UIBlock()
        add(object1)

        object1.positioner.width.set(percent(48f))

        val object2 = UIBlock()
        add(object2)

        object2.positioner.x.set(CoordinateValues.percent(52f))
        object2.positioner.width.set(percent(48f))
    }

    override fun onMousePress(event: MouseEvent) {
        println("Mouse pressed!")
    }

    override fun onMouseRelease(event: MouseEvent) {
        println("Mouse released!")
    }

}