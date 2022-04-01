package org.saar.gui.style.boxsizing

import org.saar.gui.UINode

class BoxSizing(private val container: UINode) : ReadonlyBoxSizing {

    override fun getContentWidth() = this.container.style.width.get()

    override fun getContentHeight() = this.container.style.height.get()

    override fun getBoxWidth() = getContentWidth() +
            this.container.style.borders.top +
            this.container.style.borders.bottom

    override fun getBoxHeight() = getContentHeight() +
            this.container.style.borders.top +
            this.container.style.borders.bottom

    override fun getSpaceWidth() = getBoxWidth() +
            this.container.style.margin.top +
            this.container.style.margin.bottom

    override fun getSpaceHeight() = getBoxHeight() +
            this.container.style.margin.left +
            this.container.style.margin.right
}