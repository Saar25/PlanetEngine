package org.saar.gui.style.alignment

import org.saar.gui.UINode
import org.saar.gui.style.StyleProperty

interface ReadonlyAlignment : StyleProperty {

    fun getX(child: UINode): Int

    fun getY(child: UINode): Int

}