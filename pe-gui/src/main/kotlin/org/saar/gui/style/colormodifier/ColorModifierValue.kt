package org.saar.gui.style.colormodifier

import org.joml.Vector4fc
import org.saar.gui.UIChildNode

fun interface ColorModifierValue {

    fun compute(container: UIChildNode): Vector4fc

}