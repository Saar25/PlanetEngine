package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlock
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.style.IStyle
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UIElement {

    val style: IStyle

    val uiBlock: UIBlock

    val children: List<UIElement> get() = emptyList()

    fun render(context: RenderContext) {
        UIBlockRenderer.render(context, this.uiBlock)
        renderText(context)
        this.children.forEach { it.render(context) }
    }

    fun renderText(context: RenderContext) {
        children.forEach { it.renderText(context) }
    }

    fun onMouseClickEvent(event: ClickEvent) = false

    fun onMouseMoveEvent(event: MoveEvent) = Unit

    fun onKeyPressEvent(event: KeyEvent) = Unit

    fun onKeyReleaseEvent(event: KeyEvent) = Unit

    fun onKeyRepeatEvent(event: KeyEvent) = Unit

    fun contains(mx: Int, my: Int) = this.uiBlock.inTouch(mx, my)

    fun update() = Unit

    fun delete() = Unit

}