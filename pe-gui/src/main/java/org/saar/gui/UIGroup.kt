package org.saar.gui

import org.saar.gui.render.UIRenderer
import org.saar.gui.style.Style

class UIGroup(parent: UIElement) : UIChildContainer {

    override val uiComponents = mutableListOf<UIComponent>()

    override val uiContainers = mutableListOf<UIContainer>()

    override val style = Style(this)

    override var parent: UIElement = parent

    private val renderer = lazy { UIRenderer() }

    fun add(uiComponent: UIComponent) {
        this.uiComponents.add(uiComponent)
        uiComponent.parent = this
    }

    fun add(uiContainer: UIChildContainer) {
        this.uiContainers.add(uiContainer)
        uiContainer.parent = this
    }

    override fun delete() {
        super.delete()
        if (this.renderer.isInitialized()) {
            this.renderer.value.delete()
        }
    }
}