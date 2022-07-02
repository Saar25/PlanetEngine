package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.ParentStyle

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

    override fun onKeyPressEvent(event: KeyboardEvent) {
        this.uiInputHelper.onKeyPressEvent(event)
        this.children.forEach { it.onKeyPressEvent(event) }
    }

    override fun onKeyReleaseEvent(event: KeyboardEvent) {
        this.uiInputHelper.onKeyReleaseEvent(event)
        this.children.forEach { it.onKeyReleaseEvent(event) }
    }

    override fun onKeyRepeatEvent(event: KeyboardEvent) {
        this.uiInputHelper.onKeyRepeatEvent(event)
        this.children.forEach { it.onKeyRepeatEvent(event) }
    }

    override fun onMouseMoveEvent(event: MouseEvent) {
        val inside = contains(event.x, event.y)
        this.uiInputHelper.onMouseMoveEvent(event, inside)
        this.children.forEach { it.onMouseMoveEvent(event) }
    }

    override fun onMousePressEvent(event: MouseEvent): Boolean {
        return this.uiInputHelper.onMousePressEvent(event).also {
            this.children.forEach { it.onMousePressEvent(event) }
        }
    }

    override fun onMouseReleaseEvent(event: MouseEvent): Boolean {
        return this.uiInputHelper.onMouseReleaseEvent(event).also {
            this.children.forEach { it.onMouseReleaseEvent(event) }
        }
    }
}