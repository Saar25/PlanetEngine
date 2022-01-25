package org.saar.gui.style.position

import org.saar.gui.UIChildNode

class Position(private val container: UIChildNode) : ReadonlyPosition {

    override var value: PositionValue = PositionValues.relative

    override fun getX() = this.value.computeAxisX(this.container)

    override fun getY() = this.value.computeAxisY(this.container)

}