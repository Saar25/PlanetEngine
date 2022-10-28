package org.saar.gui.style.axisalignment

import org.saar.gui.UIChildNode

object NoAxisAlignment : ReadonlyAxisAlignment {
    override val value: AxisAlignmentValue = AxisAlignmentValues.start

    override fun getX(child: UIChildNode) = 0

    override fun getY(child: UIChildNode) = 0
}