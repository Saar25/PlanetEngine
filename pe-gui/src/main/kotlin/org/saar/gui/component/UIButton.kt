package org.saar.gui.component

import org.jproperty.property.SimpleBooleanProperty
import org.saar.gui.UIComponent
import org.saar.gui.UIText
import org.saar.gui.event.EventHandler
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours

class UIButton : UIComponent() {

    private val pressedProperty = SimpleBooleanProperty()

    private var onAction: EventHandler<MouseEvent>? = null

    val uiText = UIText("Button")

    override val children = listOf(this.uiText).onEach { it.parent = this }

    init {
        style.padding.set(30, 100)
        style.borders.set(2)
    }

    fun setOnAction(onAction: EventHandler<MouseEvent>) {
        this.onAction = onAction
    }

    override fun onMousePress(event: MouseEvent) {
        if (event.button.isPrimary) {
            this.pressedProperty.set(true)
            this.style.colourModifier.set(1.5f)
            println(style.colourModifier.multiply)
        }
    }

    override fun onMouseRelease(event: MouseEvent) {
        if (event.button.isPrimary) {
            if (this.pressedProperty.get()) {
                this.pressedProperty.set(false)
                this.onAction?.handle(event)
            }
            println(style.colourModifier.multiply)
            this.style.colourModifier.set(1f)
        }
    }

    override fun onMouseEnter(event: MouseEvent) = Unit

    override fun onMouseExit(event: MouseEvent) {
        this.pressedProperty.set(false)
        println(style.colourModifier.multiply)
        this.style.colourModifier.set(1f)
    }

    init {
        this.style.backgroundColour.set(Colours.GRAY)
        this.style.borderColour.set(Colours.DARK_GRAY)
    }
}