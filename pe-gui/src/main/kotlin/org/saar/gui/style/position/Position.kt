package org.saar.gui.style.position

import org.saar.gui.UIChildNode

class Position(private val container: UIChildNode, default: PositionValue = PositionValues.relative) : ReadonlyPosition {

    override var value: PositionValue = default

    override fun getX() = this.value.computeAxisX(this.container)

    override fun getY() = this.value.computeAxisY(this.container)

}