package org.saar.core.common.r3d

import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderParentNode
import org.saar.core.renderer.forward.ForwardRenderParentNode

class NodeGroup3D(vararg nodes: Node3D) : ParentNode, ForwardRenderParentNode, DeferredRenderParentNode {

    override val children: MutableList<Node3D> = nodes.toMutableList()

    private val forwardRenderer = lazy { Renderer3D() }
    private val deferredRenderer = lazy { DeferredRenderer3D() }

    fun add(node: Node3D) {
        this.children.add(node)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        this.forwardRenderer.value.render(context, *models)
    }

    override fun renderDeferred(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        this.deferredRenderer.value.render(context, *models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }

        if (this.forwardRenderer.isInitialized()) {
            this.forwardRenderer.value.delete()
        }
        if (this.deferredRenderer.isInitialized()) {
            this.deferredRenderer.value.delete()
        }
    }
}