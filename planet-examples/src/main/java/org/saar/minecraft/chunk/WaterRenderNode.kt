package org.saar.minecraft.chunk

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.minecraft.World

class WaterRenderNode(private val world: World) : ForwardRenderNode {

    override fun renderForward(context: RenderContext) = WaterRenderer.render(context, this.world)
}