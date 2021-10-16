package org.saar.gui.style.redius

object NoStyleRadiuses : ReadonlyStyleRadiuses {
    override val topRight: Int = 0
    override val topLeft: Int = 0
    override val bottomRight: Int = 0
    override val bottomLeft: Int = 0

    override fun isZero(): Boolean = true
}