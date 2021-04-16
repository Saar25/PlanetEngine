package org.saar.example.gui

import org.saar.gui.UIBlock
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours
import org.saar.gui.style.value.CoordinateValues
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.gui.style.value.LengthValues.percent
import org.saar.lwjgl.opengl.textures.Texture2D

class MyUIComponent : UIComponent() {

    init {
        this.style.x.set(center())
        this.style.y.set(center())
        this.style.width.set(percent(80f))
        this.style.height.set(percent(80f))

        this.style.radiuses.set(20)
        this.style.borders.set(2)
        this.style.backgroundColour.set(Colours.CYAN)
        this.style.borderColour.set(Colours.LIGHT_GREY)

        val object1 = UIBlock()
        object1.discardMap = Texture2D.of("/assets/barrel/barrel.diffuse.png")
        add(object1)

        object1.style.width.set(percent(48f))

        val object2 = UIBlock()
        add(object2)

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