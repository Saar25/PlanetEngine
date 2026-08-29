package org.saar.minecraft.chunk

import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode
import org.saar.minecraft.World

class ChunkRenderNode(private val world: World) : ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override fun renderForward(context: ForwardRenderContext) = ChunkRenderer.render(context.camera, this.world)

    override fun renderDeferred(context: DeferredRenderContext) = ChunkRenderer.render(context.camera, this.world)

    override fun renderShadows(context: ShadowsRenderContext) = ChunkRenderer.render(context.camera, this.world)
}
