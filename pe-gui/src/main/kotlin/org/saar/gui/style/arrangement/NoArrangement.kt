package org.saar.gui.style.arrangement

import org.saar.gui.UIChildNode

object NoArrangement : ReadonlyArrangement {
    override val value: ArrangementValue = ArrangementValues.start

    override fun getX(child: UIChildNode) = 0

    override fun getY(child: UIChildNode) = 0
}