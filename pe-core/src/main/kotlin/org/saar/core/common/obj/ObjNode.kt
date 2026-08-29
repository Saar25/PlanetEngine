package org.saar.core.common.obj

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

class ObjNode(val model: ObjModel) : Node, ComposableNode, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    override val components = NodeComponentGroup(TransformComponent(this.model.transform))

    override fun renderForward(context: ForwardRenderContext) {
        ObjRenderer.render(context, this.model)
    }

    override fun renderDeferred(context: DeferredRenderContext) {
        ObjDeferredRenderer.render(context, this.model)
    }

    override fun renderShadows(context: ShadowsRenderContext) {
        val deferredRenderContext = DeferredRenderContext(context, context.camera)
        ObjDeferredRenderer.render(deferredRenderContext, this.model)
    }

    override fun update() = this.components.update(this)

    override fun delete() {
        this.model.delete()
        this.components.delete(this)
    }
}