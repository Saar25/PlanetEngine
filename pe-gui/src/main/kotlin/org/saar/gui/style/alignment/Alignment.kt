package org.saar.gui.style.alignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

class Alignment(private val container: UIParentNode) : ReadonlyAlignment {

    override var value: AlignmentValue = AlignmentValues.horizontal

    override fun getX(child: UIChildNode) = this.value.computeAxisX(this.container, child)

    override fun getY(child: UIChildNode) = this.value.computeAxisY(this.container, child)
}