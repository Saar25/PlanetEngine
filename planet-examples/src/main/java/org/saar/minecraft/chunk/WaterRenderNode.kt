package org.saar.minecraft.chunk

import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.minecraft.World

class WaterRenderNode(private val world: World) : ForwardRenderNode, DeferredRenderNode {

    override fun renderForward(context: ForwardRenderContext) = WaterRenderer.render(context.camera, this.world)

    override fun renderDeferred(context: DeferredRenderContext) = WaterRenderer.render(context.camera, this.world)
}
