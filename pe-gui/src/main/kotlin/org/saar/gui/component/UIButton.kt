package org.saar.gui.component

import org.jproperty.property.SimpleBooleanProperty
import org.saar.gui.UIBlock
import org.saar.gui.UIComponent
import org.saar.gui.UIText
import org.saar.gui.event.EventHandler
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours
import org.saar.gui.style.position.PositionValues

class UIButton : UIComponent() {

    private val pressedProperty = SimpleBooleanProperty()

    private var onAction: EventHandler<MouseEvent>? = null

    val uiBackground = UIBlock().apply {
        style.borders.set(2)
        style.position.value = PositionValues.absolute
    }

    val uiText = UIText("Button")

    override val children = listOf(this.uiBackground, this.uiText).onEach { it.parent = this }

    init {
        style.padding.set(30, 100)
    }

    fun setOnAction(onAction: EventHandler<MouseEvent>) {
        this.onAction = onAction
    }

    override fun onMousePress(event: MouseEvent) {
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
        this.style.backgroundColour.set(Colours.GRAY)
        this.style.borderColour.set(Colours.DARK_GRAY)
    }
}