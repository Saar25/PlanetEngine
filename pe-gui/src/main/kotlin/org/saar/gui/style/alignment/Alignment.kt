package org.saar.gui.style.alignment

import org.saar.gui.UINode
import org.saar.gui.UIParentNode
import org.saar.gui.style.value.AlignmentValue
import org.saar.gui.style.value.AlignmentValues

class Alignment(private val container: UIParentNode) : ReadonlyAlignment {

    var value: AlignmentValue = AlignmentValues.horizontal

    override fun getX(child: UINode) = this.value.computeAxisX(this.container, child)

    override fun getY(child: UINode) = this.value.computeAxisY(this.container, child)
}