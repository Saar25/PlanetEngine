package org.saar.gui.style.redius

import org.jproperty.constant.ConstantIntegerProperty

object NoRadius : ReadonlyRadius {
    override val topRight = ConstantIntegerProperty(0)
    override val topLeft = ConstantIntegerProperty(0)
    override val bottomRight = ConstantIntegerProperty(0)
    override val bottomLeft = ConstantIntegerProperty(0)

    override fun isZero(): Boolean = true
}