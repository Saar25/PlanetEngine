package org.saar.core.renderer

import org.saar.core.renderer.state.DefaultRenderState
import org.saar.core.renderer.state.RenderState

interface RenderPass {

    val renderState: RenderState get() = DefaultRenderState

    fun render(context: RenderContext)

    fun delete()

}