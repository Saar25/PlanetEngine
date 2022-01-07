package org.saar.gui.style.alignment

import org.saar.gui.UIChildElement
import org.saar.gui.UIParentElement
import org.saar.gui.style.value.AlignmentValue
import org.saar.gui.style.value.AlignmentValues

class Alignment(private val container: UIParentElement) : ReadonlyAlignment {

    var value: AlignmentValue = AlignmentValues.horizontal()

    override fun getX(child: UIChildElement) = this.value.computeAxisX(this.container, child)

    override fun getY(child: UIChildElement) = this.value.computeAxisY(this.container, child)
}