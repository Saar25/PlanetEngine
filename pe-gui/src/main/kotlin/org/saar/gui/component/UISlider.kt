package org.saar.gui.component

import org.jproperty.type.FloatProperty
import org.jproperty.type.SimpleFloatProperty
import org.saar.gui.UIBlockElement
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Colours
import org.saar.gui.style.value.CoordinateValues.percent
import org.saar.maths.utils.Maths

class UISlider : UIComponent() {

    val valueProperty: FloatProperty = SimpleFloatProperty(0f)

    val dynamicValueProperty: FloatProperty = SimpleFloatProperty(0f)

    val min: FloatProperty = SimpleFloatProperty(0f)
    val max: FloatProperty = SimpleFloatProperty(100f)

    private val uiTruck = UIBlockElement().also { it.parent = this }
    private val uiThumb = UIBlockElement().also { it.parent = this }

    override val children = listOf(this.uiTruck, this.uiThumb)

    init {
        initUiTruck()
        initUiThumb()
    }

    private fun initUiTruck() {
        this.uiTruck.style.backgroundColour.set(Colours.GRAY)
        this.uiTruck.style.borderColour.set(Colours.DARK_GRAY)
        this.uiTruck.style.borders.set(2)
    }

    private fun initUiThumb() {
        this.uiThumb.style.backgroundColour.set(Colours.DARK_GRAY)
        this.uiThumb.style.width.set(20)
    }

    override fun onMousePress(event: MouseEvent) {
        onMouseDrag(event)
    }

    override fun onMouseRelease(event: MouseEvent) {
        val value = dynamicValueProperty.get()
        this.valueProperty.set(value)
    }

    override fun onMouseDrag(event: MouseEvent) {
        val x1 = uiTruck.style.x.get()
        val w1 = uiTruck.style.width.get()
        val w2 = uiThumb.style.width.get()

        val xMin = x1 + w2 / 2
        val xMax = x1 + w1 - w2 / 2

        val x2 = Maths.clamp(event.x, xMin, xMax)
        val relativePosition = (x2 - xMin).toFloat()

        val position = relativePosition / w1
        uiThumb.style.x.value = percent(position * 100)

        val value = relativePosition / (w1 - w2)
        dynamicValueProperty.value = value * (max.get() - min.get()) + min.get()
    }
}