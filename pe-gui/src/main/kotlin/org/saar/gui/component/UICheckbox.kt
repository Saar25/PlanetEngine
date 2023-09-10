package org.saar.gui.component

import org.jproperty.property.SimpleBooleanProperty
import org.saar.gui.Defaults
import org.saar.gui.UIBlock
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.border.BorderValues
import org.saar.gui.style.discardmap.DiscardMapValue
import org.saar.gui.style.length.LengthValues
import org.saar.gui.style.redius.RadiusValues
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter
import org.saar.lwjgl.opengl.texture.parameter.TextureParameter
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue

private val discardMap = Texture2D.of("/assets/gui/checkbox.png").apply {
    applyParameters(arrayOf<TextureParameter>(
        TextureMinFilterParameter(MinFilterValue.LINEAR),
        TextureMagFilterParameter(MagFilterValue.LINEAR),
    ))
}

class UICheckbox : UIComponent() {

    private val checkedProperty = SimpleBooleanProperty()

    private val uiChild = UIBlock().apply {
        style.borders.set(BorderValues.inherit)
        style.radius.set(RadiusValues.inherit)

        style.discardMap.value = DiscardMapValue {
            if (checkedProperty.get()) discardMap else Texture2D.NULL
        }
    }

    override val children = listOf(this.uiChild).onEach { it.parent = this }

    override val renderSelf: Boolean = false

    init {
        style.radius.set(2)
        style.borders.set(2)
        style.height.value = LengthValues.ratio(1f)
        style.borderColour.set(Defaults.secondColour)
        style.backgroundColour.set(Defaults.mainColour)
    }

    override fun onMousePress(event: MouseEvent) {
        if (event.button.isPrimary) {
            this.style.colourModifier.set(1.3f)
        }
    }

    override fun onMouseRelease(event: MouseEvent) {
        if (event.button.isPrimary && isMouseOver) {
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