package org.saar.gui.style.alignment

import org.saar.gui.UIChildElement
import org.saar.gui.style.value.AlignmentValue
import org.saar.gui.style.value.AlignmentValues

class Alignment(private val container: UIChildElement) : ReadonlyAlignment {

    val value: AlignmentValue = AlignmentValues.horizontal()

    override fun getX(child: UIChildElement): Int {
        TODO("Not yet implemented")
    }

    override fun getY(child: UIChildElement): Int {
        TODO("Not yet implemented")
    }
}