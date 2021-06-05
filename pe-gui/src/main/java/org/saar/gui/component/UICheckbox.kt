package org.saar.gui.component

import org.jproperty.type.BooleanProperty
import org.jproperty.type.SimpleBooleanProperty
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours
import org.saar.gui.style.value.LengthValues
import org.saar.lwjgl.opengl.textures.Texture2D

private val discardMap = Texture2D.of("/assets/gui/checkbox.png")

class UICheckbox : UIComponent() {

    private val checkedProperty: BooleanProperty = SimpleBooleanProperty()

    init {
        initUiObject()
        this.checkedProperty.addListener { checked ->
            if (checked.newValue) {
                this.uiBlock.discardMap = discardMap
            } else {
                this.uiBlock.discardMap = null
            }
        }
    }

    private fun initUiObject() {
        this.style.height.value = LengthValues.ratio(1f)
        this.style.borderColour.set(Colours.LIGHT_GREY)
        this.style.borders.set(1)
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