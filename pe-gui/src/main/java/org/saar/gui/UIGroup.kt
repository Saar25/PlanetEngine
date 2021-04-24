package org.saar.gui

import org.saar.gui.style.Style

class UIGroup(parent: UIElement) : UIChildContainer {

    override val uiComponents = mutableListOf<UIComponent>()

    override val uiContainers = mutableListOf<UIContainer>()

    override val style = Style(this)

    override var parent: UIElement = parent

    fun add(uiComponent: UIComponent) {
        this.uiComponents.add(uiComponent)
        uiComponent.parent = this
    }

    fun add(uiContainer: UIChildContainer) {
        this.uiContainers.add(uiContainer)
        uiContainer.parent = this
    }
}