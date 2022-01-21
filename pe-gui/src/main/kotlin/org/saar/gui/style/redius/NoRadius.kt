package org.saar.gui.style.redius

object NoRadius : ReadonlyRadius {
    override val topRight = 0
    override val topLeft = 0
    override val bottomRight = 0
    override val bottomLeft = 0

    override fun isZero(): Boolean = true
}