package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.ParentStyle

interface UIParentNode : UINode {

    override val style: ParentStyle

    override var activeElement: UINode

    val children: List<UINode>

    override fun render(context: RenderContext) {
        UIBlockRenderer.render(context, this)
        this.children.forEach { it.render(context) }
    }

    override fun update() = this.children.forEach { it.update() }

    override fun delete() = this.children.forEach { it.delete() }

    override fun contains(x: Int, y: Int) =
        MouseDetection.contains(this, x, y) || this.children.any { it.contains(x, y) }

    override fun onMousePressEvent(event: MouseEvent): Boolean {
        return this.children.asReversed().any { it.onMousePressEvent(event) }
    }

    override fun onMouseReleaseEvent(event: MouseEvent): Boolean {
        return this.children.asReversed().any { it.onMouseReleaseEvent(event) }
    }

    override fun onMouseMoveEvent(event: MouseEvent) {
        this.children.asReversed().forEach { it.onMouseMoveEvent(event) }
    }

    override fun onKeyPressEvent(event: KeyboardEvent) {
        this.children.asReversed().forEach { it.onKeyPressEvent(event) }
    }

    override fun onKeyReleaseEvent(event: KeyboardEvent) {
        this.children.asReversed().forEach { it.onKeyReleaseEvent(event) }
    }

    override fun onKeyRepeatEvent(event: KeyboardEvent) {
        this.children.asReversed().forEach { it.onKeyRepeatEvent(event) }
    }
}