package org.saar.gui.style.margin

import org.jproperty.constant.ConstantInteger

object NoMargin : ReadonlyMargin {
    override val top = ConstantInteger(0)
    override val right = ConstantInteger(0)
    override val bottom = ConstantInteger(0)
    override val left = ConstantInteger(0)
}