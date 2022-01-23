package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.p2d.RenderNode2D
import org.saar.gui.style.Style
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent
import org.saar.lwjgl.glfw.input.mouse.ClickEvent
import org.saar.lwjgl.glfw.input.mouse.MoveEvent

interface UINode : RenderNode2D, ForwardRenderNode, DeferredRenderNode {

    val style: Style

    override fun renderDeferred(context: RenderContext) = render(context)

    override fun renderForward(context: RenderContext) = render(context)

    override fun render2D(context: RenderContext) = render(context)

    fun render(context: RenderContext)

    fun contains(x: Int, y: Int): Boolean

    fun onMouseClickEvent(event: ClickEvent) = false

    fun onMouseMoveEvent(event: MoveEvent) = Unit

    fun onKeyPressEvent(event: KeyEvent) = Unit

    fun onKeyReleaseEvent(event: KeyEvent) = Unit

    fun onKeyRepeatEvent(event: KeyEvent) = Unit

}