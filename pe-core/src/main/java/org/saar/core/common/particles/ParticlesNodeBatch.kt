package org.saar.core.common.particles

import org.saar.core.node.ParentNode
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode

class ParticlesNodeBatch(vararg nodes: ParticlesNode) : ParentNode,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override val children: MutableList<ParticlesNode> = nodes.toMutableList()

    fun add(node: ParticlesNode) {
        this.children.add(node)
    }

    override fun renderForward(context: ForwardRenderContext) {
        val models = this.children.map { it.model }
        ParticlesRenderer.render(context, models)
    }

    override fun renderDeferred(context: DeferredRenderContext) {
        val models = this.children.map { it.model }
        ParticlesDeferredRenderer.render(context, models)
    }

    override fun renderShadows(context: ShadowsRenderContext) {
        val models = this.children.map { it.model }
        ParticlesDeferredRenderer.render(context, models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}