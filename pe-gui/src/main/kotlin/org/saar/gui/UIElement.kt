package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.p2d.RenderNode2D
import org.saar.gui.block.UIBlock
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.style.IStyle
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UIElement : RenderNode2D, ForwardRenderNode, DeferredRenderNode {

    val style: IStyle

    val uiBlock: UIBlock

    val children: List<UIElement> get() = emptyList()

    override fun renderDeferred(context: RenderContext) = render(context)

    override fun renderForward(context: RenderContext) = render(context)

    override fun render2D(context: RenderContext) = render(context)

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

    override fun update() = Unit

    override fun delete() = Unit

}