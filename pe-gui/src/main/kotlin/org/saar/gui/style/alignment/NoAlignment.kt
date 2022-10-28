package org.saar.gui.style.alignment

import org.saar.gui.UIChildNode

object NoAlignment : ReadonlyAlignment {
    override val value: AlignmentValue = AlignmentValues.none

    override fun getX(child: UIChildNode) = 0

    override fun getY(child: UIChildNode) = 0
}