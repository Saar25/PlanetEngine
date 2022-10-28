package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.p2d.RenderNode2D
import org.saar.gui.event.KeyboardEvent
import org.saar.gui.event.MouseEvent
import org.saar.gui.style.Style

interface UINode : RenderNode2D, ForwardRenderNode, DeferredRenderNode {

    val style: Style

    val uiInputHelper: UIInputHelper

    val isMouseOver get() = this.uiInputHelper.isMouseOver

    val isMousePressed get() = this.uiInputHelper.isMousePressed

    val isFocused: Boolean get() = this.uiInputHelper.isFocused

    override fun renderDeferred(context: RenderContext) = render(context)

    override fun renderForward(context: RenderContext) = render(context)

    override fun render2D(context: RenderContext) = render(context)

    fun render(context: RenderContext)

    fun contains(x: Int, y: Int): Boolean

    fun onMousePressEvent(event: MouseEvent) = false

    fun onMouseReleaseEvent(event: MouseEvent) = false

    fun onMouseMoveEvent(event: MouseEvent) = Unit

    fun onKeyPressEvent(event: KeyboardEvent) = Unit

    fun onKeyReleaseEvent(event: KeyboardEvent) = Unit

    fun onKeyRepeatEvent(event: KeyboardEvent) = Unit

}