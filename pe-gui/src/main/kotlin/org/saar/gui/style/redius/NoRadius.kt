package org.saar.gui.style.redius

import org.jproperty.constant.ConstantInteger

object NoRadius : ReadonlyRadius {
    override val topRight = ConstantInteger(0)
    override val topLeft = ConstantInteger(0)
    override val bottomRight = ConstantInteger(0)
    override val bottomLeft = ConstantInteger(0)

    override fun isZero(): Boolean = true
}