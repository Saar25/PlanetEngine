package org.saar.gui.style.position

import org.saar.gui.UIChildNode
import org.saar.gui.style.value.PositionValue
import org.saar.gui.style.value.PositionValues

class Position(private val container: UIChildNode) : ReadonlyPosition {

    var value: PositionValue = PositionValues.relative()

    override fun getX() = this.value.computeAxisX(this.container)

    override fun getY() = this.value.computeAxisY(this.container)

}