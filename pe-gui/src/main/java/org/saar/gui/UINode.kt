package org.saar.gui

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UINode : Node, UIElement, ForwardRenderNode {

    fun onMouseClickEvent(event: ClickEvent): Boolean {
        return false
    }

    fun onMouseMoveEvent(event: MoveEvent) {

    }

    fun render(context: RenderContext)

    override fun renderForward(context: RenderContext) = render(context)

    override fun delete()
}