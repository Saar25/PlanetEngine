package org.saar.gui

interface UIContainer : UIElement {

    val uiComponents: List<UIComponent>

    val uiContainers: List<UIContainer>

}