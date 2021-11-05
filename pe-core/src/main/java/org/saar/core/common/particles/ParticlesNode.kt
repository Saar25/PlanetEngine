package org.saar.core.common.particles

import org.saar.core.node.NodeComponentGroup
import org.saar.core.node.ComposableNode
import org.saar.core.common.components.TransformComponent
import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

open class ParticlesNode(val model: ParticlesModel, components: NodeComponentGroup) :
    Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode, ComposableNode {

    constructor(model: ParticlesModel) : this(model, NodeComponentGroup())

    final override val components: NodeComponentGroup = NodeComponentGroup(
        components, TransformComponent(model.transform))

    init {
        this.components.start(this)
    }

    final override fun renderForward(context: RenderContext) {
        ParticlesRenderer.render(context, this.model)
    }

    final override fun renderDeferred(context: RenderContext) {
        ParticlesDeferredRenderer.render(context, this.model)
    }

    final override fun renderShadows(context: RenderContext) {
        ParticlesDeferredRenderer.render(context, this.model)
    }

    final override fun update() {
        this.components.update(this)
    }

    final override fun delete() {
        this.model.delete()
        this.components.delete(this)
    }
}