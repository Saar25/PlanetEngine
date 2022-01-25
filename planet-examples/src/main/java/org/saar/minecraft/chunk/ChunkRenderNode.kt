package org.saar.minecraft.chunk

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode
import org.saar.minecraft.World

class ChunkRenderNode(private val world: World) : ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: RenderContext) = ChunkRenderer.render(context, this.world)

    override fun renderDeferred(context: RenderContext) = ChunkRenderer.render(context, this.world)

    override fun renderShadows(context: RenderContext) = ChunkRenderer.render(context, this.world)
}