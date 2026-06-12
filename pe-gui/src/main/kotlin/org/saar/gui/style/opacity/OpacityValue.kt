package org.saar.gui.style.opacity

import org.saar.gui.UIChildNode

fun interface OpacityValue {

    fun compute(container: UIChildNode): Float

}