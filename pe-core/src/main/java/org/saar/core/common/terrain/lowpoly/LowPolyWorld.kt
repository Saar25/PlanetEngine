package org.saar.core.common.terrain.lowpoly

import org.joml.Vector2fc
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.NodeBatch3D
import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderParentNode
import org.saar.core.renderer.forward.ForwardRenderParentNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class LowPolyWorld(private val configuration: LowPolyTerrainConfiguration) : ParentNode,
    ForwardRenderParentNode, DeferredRenderParentNode, ShadowsRenderNode {

    private val batch = NodeBatch3D()

    override val children: List<Node3D> = batch.children

    fun createTerrain(position: Vector2fc) {
        this.batch.add(LowPolyTerrain(position, this.configuration))
    }

    override fun renderForward(context: RenderContext) {
        this.batch.renderForward(context)
    }

    override fun renderDeferred(context: RenderContext) {
        this.batch.renderDeferred(context)
    }

    override fun renderShadows(context: RenderContext) {
        this.batch.renderShadows(context)
    }

    override fun delete() {
        this.batch.delete()
    }
}