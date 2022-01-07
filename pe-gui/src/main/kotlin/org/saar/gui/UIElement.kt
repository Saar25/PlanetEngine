package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlock
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.style.ParentStyle
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UIElement : UIChildNode, UIParentNode {

    val uiBlock: UIBlock

    override val style: ParentStyle

    override var parent: UIParentNode

    override val children: List<UINode> get() = emptyList()

    override fun renderDeferred(context: RenderContext) = render(context)

    override fun renderForward(context: RenderContext) = render(context)

    override fun render2D(context: RenderContext) = render(context)

    override fun render(context: RenderContext) {
        UIBlockRenderer.render(context, this)
        this.children.forEach { it.render(context) }
    }

    fun contains(mx: Int, my: Int) = this.uiBlock.inTouch(mx, my)

    override fun update() = this.children.forEach { it.update() }

    override fun delete() = this.children.forEach { it.delete() }


}