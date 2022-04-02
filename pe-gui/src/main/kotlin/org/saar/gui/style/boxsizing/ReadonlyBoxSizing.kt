package org.saar.gui.style.boxsizing

import org.saar.gui.style.StyleProperty

interface ReadonlyBoxSizing : StyleProperty {

    fun getContentWidth(): Int

    fun getContentHeight(): Int

    fun getBoxWidth(): Int

    fun getBoxHeight(): Int

    fun getSpaceWidth(): Int

    fun getSpaceHeight(): Int

}