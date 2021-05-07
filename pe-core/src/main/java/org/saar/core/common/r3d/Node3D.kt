package org.saar.core.common.r3d

import org.saar.core.behavior.BehaviorGroup
import org.saar.core.behavior.BehaviorNode
import org.saar.core.common.behaviors.TransformBehavior
import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class Node3D(val model: Model3D, behaviors: BehaviorGroup) :
    Node, ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode, BehaviorNode {

    constructor(model: Model3D) : this(model, BehaviorGroup())

    override val behaviors: BehaviorGroup = BehaviorGroup(
        behaviors, TransformBehavior(model.transform))

    init {
        this.behaviors.start(this)
    }

    override fun renderForward(context: RenderContext) {
        Renderer3D.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        DeferredRenderer3D.render(context, this.model)
    }

    override fun renderShadows(context: RenderContext) {
        DeferredRenderer3D.render(context, this.model)
    }

    override fun update() {
        this.behaviors.update(this)
    }

    override fun delete() {
        this.model.delete()
        this.behaviors.delete(this)
    }
}