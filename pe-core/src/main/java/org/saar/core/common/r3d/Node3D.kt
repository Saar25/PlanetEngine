package org.saar.core.common.r3d

import org.saar.core.common.components.TransformComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.Node
import org.saar.core.node.NodeComponentGroup
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

open class Node3D(val model: Model3D, components: NodeComponentGroup) :
    Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode, ComposableNode {

    constructor(model: Model3D) : this(model, NodeComponentGroup())

    final override val components: NodeComponentGroup = NodeComponentGroup(
        components, TransformComponent(model.transform))

    final override fun renderForward(context: RenderContext) {
        Renderer3D.render(context, this.model)
    }

    final override fun renderDeferred(context: RenderContext) {
        DeferredRenderer3D.render(context, this.model)
    }

    final override fun renderShadows(context: RenderContext) {
        DeferredRenderer3D.render(context, this.model)
    }

    final override fun update() {
        this.components.update(this)
    }

    final override fun delete() {
        this.model.delete()
        this.components.delete(this)
    }
}