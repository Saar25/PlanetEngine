package org.saar.gui.component

import org.jproperty.Observable
import org.jproperty.property.SimpleBooleanProperty
import org.saar.gui.UIBlock
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours
import org.saar.gui.style.length.LengthValues
import org.saar.lwjgl.opengl.texture.Texture2D

private val discardMap = Texture2D.of("/assets/gui/checkbox.png")

class UICheckbox : UIComponent() {

    private val checkedProperty = SimpleBooleanProperty()

    private val uiChild = UIBlock().apply {
        style.borders.set(1)
    }

    override val children = listOf(this.uiChild).onEach { it.parent = this }

    init {
        this.style.height.value = LengthValues.ratio(1f)
        this.style.borderColour.set(Colours.LIGHT_GRAY)

        this.checkedProperty.addListener { _: Observable ->
            if (this.checkedProperty.get()) {
                this.uiChild.discardMap = discardMap
            } else {
                this.uiChild.discardMap = Texture2D.NULL
            }
        }
    }

    override fun onMousePress(event: MouseEvent) {
        if (event.button.isPrimary) {
            this.style.colourModifier.set(1.3f)
        }
    }

    override fun onMouseRelease(event: MouseEvent) {
        if (event.button.isPrimary && isMouseHover) {
            val current = this.checkedProperty.get()
            this.checkedProperty.set(!current)

            this.style.colourModifier.set(1f)
        }
    }

    override fun onMouseEnter(event: MouseEvent) {
        this.style.colourModifier.set(1.2f)
    }

    override fun onMouseExit(event: MouseEvent) {
        this.style.colourModifier.set(1f)
    }
}