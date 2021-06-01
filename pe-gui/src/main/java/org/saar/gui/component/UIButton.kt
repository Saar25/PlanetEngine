package org.saar.gui.component

import org.jproperty.type.BooleanProperty
import org.jproperty.type.SimpleBooleanProperty
import org.saar.gui.UIComponent
import org.saar.gui.event.EventHandler
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours

class UIButton : UIComponent() {

    private val pressedProperty: BooleanProperty = SimpleBooleanProperty()

    private var onAction: EventHandler<MouseEvent>? = null

    fun setOnAction(onAction: EventHandler<MouseEvent>) {
        this.onAction = onAction
    }

    override fun onMousePress(event: MouseEvent) {
        println(event)
        if (event.button.isPrimary) {
            this.pressedProperty.set(true)
            this.style.colourModifier.set(1.5f)
        }
    }

    override fun onMouseRelease(event: MouseEvent) {
        if (event.button.isPrimary) {
            if (this.pressedProperty.get()) {
                this.pressedProperty.set(false)
                this.onAction?.handle(event)
            }
            this.style.colourModifier.set(1f)
        }
    }

    override fun onMouseEnter(event: MouseEvent) = Unit

    override fun onMouseExit(event: MouseEvent) {
        this.pressedProperty.set(false)
        this.style.colourModifier.set(1f)
    }

    init {
        this.style.backgroundColour.set(Colours.GREY)
        this.style.borderColour.set(Colours.DARK_GREY)
        this.style.borders.set(2)
    }
}