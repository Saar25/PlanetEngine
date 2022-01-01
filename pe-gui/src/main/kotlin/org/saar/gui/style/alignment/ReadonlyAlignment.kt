package org.saar.gui.style.alignment

import org.saar.gui.UIChildElement
import org.saar.gui.style.StyleProperty

interface ReadonlyAlignment : StyleProperty {

    fun getX(child: UIChildElement): Int

    fun getY(child: UIChildElement): Int

}