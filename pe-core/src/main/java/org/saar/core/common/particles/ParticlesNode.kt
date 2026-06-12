package org.saar.core.common.particles

import org.saar.core.common.components.TransformComponent
import org.saar.core.common.particles.components.ParticlesMeshUpdateComponent
import org.saar.core.common.particles.components.ParticlesModelComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.Node
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class ParticlesNode(val model: ParticlesModel, components: NodeComponentGroup) :
    Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode, ComposableNode {

    constructor(model: ParticlesModel) : this(model, NodeComponentGroup())

    override val components: NodeComponentGroup = NodeComponentGroup(
        components,
        TransformComponent(this.model.transform),
        ParticlesModelComponent(this.model),
        ParticlesMeshUpdateComponent(),
    )

    override fun renderForward(context: RenderContext) {
        ParticlesRenderer.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        ParticlesDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
        ParticlesDeferredRenderer.render(context, this.model)
    }

    override fun update() {
        this.components.update(this)
    }

    override fun delete() {
        this.model.delete()
        this.components.delete(this)
    }
}