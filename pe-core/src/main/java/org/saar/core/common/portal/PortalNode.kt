package org.saar.core.common.portal

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

open class PortalNode(val model: PortalModel, components: NodeComponentGroup) :
    Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode, ComposableNode {

    constructor(model: PortalModel) : this(model, NodeComponentGroup())

    final override val components: NodeComponentGroup = NodeComponentGroup(
        components, TransformComponent(model.transform)
    )

    final override fun renderForward(context: ForwardRenderContext) {
        PortalRenderer.render(context, this.model)
    }

    final override fun renderDeferred(context: DeferredRenderContext) {
        PortalDeferredRenderer.render(context, this.model)
    }

    final override fun renderShadows(context: ShadowsRenderContext) {
        PortalDeferredRenderer.render(context, this.model)
    }

    final override fun update() {
        this.components.update(this)
    }

    final override fun delete() {
        this.model.delete()
        this.components.delete(this)
    }
}