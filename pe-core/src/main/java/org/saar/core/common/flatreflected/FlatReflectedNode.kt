package org.saar.core.common.flatreflected

import org.saar.core.node.Node
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class FlatReflectedNode(val model: FlatReflectedModel) : Node, ForwardRenderNode,
    DeferredRenderNode {

    private val forwardRenderer = lazy { FlatReflectedRenderer() }
    private val deferredRenderer = lazy { FlatReflectedDeferredRenderer() }

    override fun renderForward(context: RenderContext) {
        this.forwardRenderer.value.render(context, this.model)
    }

    override fun renderDeferred(context: RenderContext) {
        this.deferredRenderer.value.render(context, this.model)
    }

    override fun delete() {
        this.model.delete()
        if (this.forwardRenderer.isInitialized()) {
            this.forwardRenderer.value.delete()
        }
        if (this.deferredRenderer.isInitialized()) {
            this.deferredRenderer.value.delete()
        }
    }
}