package org.saar.gui.style.boxsizing

object NoBoxSizing : ReadonlyBoxSizing {

    override fun getContentWidth(): Int = 0

    override fun getContentHeight(): Int = 0

    override fun getBoxWidth(): Int = 0

    override fun getBoxHeight(): Int = 0

    override fun getSpaceWidth(): Int = 0

    override fun getSpaceHeight(): Int = 0
}