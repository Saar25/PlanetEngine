package org.saar.gui.style.axisalignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

class AxisAlignment(private val container: UIParentNode, default: AxisAlignmentValue = AxisAlignmentValues.start) : ReadonlyAxisAlignment {

    override var value: AxisAlignmentValue = default

    override fun getX(child: UIChildNode) = this.value.computeAxisX(this.container, child)

    override fun getY(child: UIChildNode) = this.value.computeAxisY(this.container, child)
}