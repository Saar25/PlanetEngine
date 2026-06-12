package org.saar.gui.component

import org.jproperty.property.FloatProperty
import org.jproperty.property.SimpleFloatProperty
import org.saar.gui.Defaults
import org.saar.gui.UIBlock
import org.saar.gui.UIComponent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.axisalignment.AxisAlignmentValues
import org.saar.gui.style.coordinate.CoordinateValues.percent
import org.saar.gui.style.length.LengthValues
import org.saar.gui.style.position.PositionValues
import org.saar.maths.utils.Maths

class UISlider : UIComponent() {

    val valueProperty: FloatProperty = SimpleFloatProperty(0f)

    val dynamicValueProperty: FloatProperty = SimpleFloatProperty(0f)

    val min: FloatProperty = SimpleFloatProperty(0f)
    val max: FloatProperty = SimpleFloatProperty(100f)

    private val uiTruck = UIBlock().apply {
        style.borders.set(2)
        style.height.value = LengthValues.percent(40f)
    }

    private val uiThumb = UIBlock().apply {
        style.backgroundColour.set(Defaults.mainColour)
        style.position.value = PositionValues.absolute
        style.width.set(10)
    }

    override val children = listOf(this.uiTruck, this.uiThumb).onEach { it.parent = this }

    override val renderSelf = false

    init {
        style.axisAlignment.value = AxisAlignmentValues.center
        style.backgroundColour.set(Defaults.backgroundColour)
        style.borderColour.set(Defaults.secondColour)
    }

    override fun onMousePress(event: MouseEvent) {
        onMouseDrag(event)
    }

    override fun onMouseRelease(event: MouseEvent) {
        val value = dynamicValueProperty.get()
        this.valueProperty.set(value)
    }

    override fun onMouseDrag(event: MouseEvent) {
        val x1 = uiTruck.style.position.getX()
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