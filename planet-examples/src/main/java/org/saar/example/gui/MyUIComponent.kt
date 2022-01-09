package org.saar.example.gui

import org.saar.gui.UIBlock
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours
import org.saar.gui.style.value.LengthValues.percent

class MyUIComponent : UIComponent() {

    private val object1 = UIBlock().apply {
        style.radiuses.set(20)
        style.borders.set(2)
        style.backgroundColour.set(Colours.CYAN)
        style.borderColour.set(Colours.LIGHT_GRAY)
        style.width.value = percent(50f)
    }

    private val object2 = UIBlock().apply {
        style.radiuses.set(20)
        style.borders.set(2)
        style.backgroundColour.set(Colours.CYAN)
        style.borderColour.set(Colours.LIGHT_GRAY)
        style.width.value = percent(50f)
    }

    override val children = listOf(this.object1, this.object2).onEach { it.parent = this }

    init {
        this.style.width.set(percent(80f))
        this.style.height.set(percent(80f))
    }

    override fun onMousePress(event: MouseEvent) {
        println("Mouse pressed!")
    }

    override fun onMouseRelease(event: MouseEvent) {
        println("Mouse released!")
    }

}