package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.style.ParentStyle
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UIParentNode : UINode {

    override val style: ParentStyle

    val children: List<UINode>

    override fun render(context: RenderContext) {
        UIBlockRenderer.render(context, this)
        this.children.forEach { it.render(context) }
    }

    override fun update() = this.children.forEach { it.update() }

    override fun delete() = this.children.forEach { it.delete() }

    override fun contains(x: Int, y: Int) =
        MouseDetection.contains(this, x, y) || this.children.any { it.contains(x, y) }

    override fun onMouseClickEvent(event: ClickEvent): Boolean {
        return this.children.asReversed().any { it.onMouseClickEvent(event) }
    }

    override fun onMouseMoveEvent(event: MoveEvent) {
        this.children.asReversed().forEach { it.onMouseMoveEvent(event) }
    }

    override fun onKeyPressEvent(event: KeyEvent) {
        this.children.asReversed().forEach { it.onKeyPressEvent(event) }
    }

    override fun onKeyReleaseEvent(event: KeyEvent) {
        this.children.asReversed().forEach { it.onKeyReleaseEvent(event) }
    }

    override fun onKeyRepeatEvent(event: KeyEvent) {
        this.children.asReversed().forEach { it.onKeyRepeatEvent(event) }
    }
}