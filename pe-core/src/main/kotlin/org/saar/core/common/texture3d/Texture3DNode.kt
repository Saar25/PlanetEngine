package org.saar.core.common.texture3d

import org.saar.core.common.components.TransformComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.Node
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderContext
import org.saar.core.renderer.shadow.ShadowsRenderNode

open class Texture3DNode(val model: Texture3DModel, components: NodeComponentGroup) :
    Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode, ComposableNode {

    constructor(model: Texture3DModel) : this(model, NodeComponentGroup())

    final override val components: NodeComponentGroup = NodeComponentGroup(
        components, TransformComponent(model.transform)
    )

    final override fun renderForward(context: ForwardRenderContext) {
        Texture3DRenderer.render(context, this.model)
    }

    final override fun renderDeferred(context: DeferredRenderContext) {
        Texture3DDeferredRenderer.render(context, this.model)
    }

    final override fun renderShadows(context: ShadowsRenderContext) {
        val deferredRenderContext = DeferredRenderContext(context, context.camera)
        Texture3DDeferredRenderer.render(deferredRenderContext, this.model)
    }

    final override fun update() {
        this.components.update(this)
    }

    final override fun delete() {
        this.model.delete()
        this.components.delete(this)
    }
}