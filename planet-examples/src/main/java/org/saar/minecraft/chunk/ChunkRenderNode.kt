package org.saar.minecraft.chunk

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.minecraft.World

class ChunkRenderNode(private val world: World) : ForwardRenderNode, DeferredRenderNode {

    override fun renderForward(context: RenderContext) = ChunkRenderer.render(context, this.world)

    override fun renderDeferred(context: RenderContext) = ChunkRenderer.render(context, this.world)
}