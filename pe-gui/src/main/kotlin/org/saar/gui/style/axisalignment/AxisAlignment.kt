package org.saar.gui.style.axisalignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

class AxisAlignment(private val container: UIParentNode) : ReadonlyAxisAlignment {

    override var value: AxisAlignmentValue = AxisAlignmentValues.start

    override fun getX(child: UIChildNode) = this.value.computeAxisX(this.container, child)

    override fun getY(child: UIChildNode) = this.value.computeAxisY(this.container, child)
}