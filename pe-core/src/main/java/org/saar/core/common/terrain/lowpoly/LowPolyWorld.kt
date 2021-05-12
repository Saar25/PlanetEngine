package org.saar.core.common.terrain.lowpoly

import org.joml.Vector2fc
import org.saar.core.common.r3d.DeferredRenderer3D
import org.saar.core.common.r3d.Node3D
import org.saar.core.common.r3d.Renderer3D
import org.saar.core.node.ParentNode
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderParentNode
import org.saar.core.renderer.forward.ForwardRenderParentNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class LowPolyWorld(private val configuration: LowPolyTerrainConfiguration) : ParentNode,
    ForwardRenderParentNode, DeferredRenderParentNode, ShadowsRenderNode {

    override val children = mutableListOf<Node3D>()

    fun createTerrain(position: Vector2fc) {
        this.children.add(LowPolyTerrain(position, this.configuration))
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        Renderer3D.render(context, *models)
    }

    override fun renderDeferred(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        DeferredRenderer3D.render(context, *models)
    }

    override fun renderShadows(context: RenderContext) {
        val models = this.children.map { it.model }.toTypedArray()
        DeferredRenderer3D.render(context, *models)
    }

    override fun delete() {
        this.children.forEach { it.delete() }
    }
}