package org.saar.gui.style.margin

import org.jproperty.constant.ConstantIntegerProperty

object NoMargin : ReadonlyMargin {
    override val top = ConstantIntegerProperty(0)
    override val right = ConstantIntegerProperty(0)
    override val bottom = ConstantIntegerProperty(0)
    override val left = ConstantIntegerProperty(0)
}