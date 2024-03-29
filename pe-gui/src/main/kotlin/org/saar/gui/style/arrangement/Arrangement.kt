package org.saar.gui.style.arrangement

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

class Arrangement(private val container: UIParentNode, default: ArrangementValue = ArrangementValues.start) : ReadonlyArrangement {

    override var value: ArrangementValue = default

    override fun getX(child: UIChildNode) = this.value.computeAxisX(this.container, child)

    override fun getY(child: UIChildNode) = this.value.computeAxisY(this.container, child)
}