package org.saar.example.gui

import org.saar.gui.UIBlock
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.length.LengthValues.pixels

class MyUIComponent : UIComponent() {

    private val object1 = UIBlock().apply {
        style.radius.set(20)
        style.borders.set(2)
        style.backgroundColour.set(Colours.CYAN)
        style.borderColour.set(Colours.LIGHT_GRAY)
        style.width.value = percent(50f)
        style.height.value = pixels(100)
    }

    private val object2 = UIBlock().apply {
        style.radius.set(20)
        style.borders.set(2)
        style.backgroundColour.set(Colours.CYAN)
        style.borderColour.set(Colours.LIGHT_GRAY)
        style.width.value = percent(50f)
        style.height.value = pixels(100)
    }

    override val children = listOf(this.object1, this.object2).onEach { it.parent = this }

    override fun onMousePress(event: MouseEvent) {
        println("Mouse pressed!")
    }

    override fun onMouseRelease(event: MouseEvent) {
        println("Mouse released!")
    }

}