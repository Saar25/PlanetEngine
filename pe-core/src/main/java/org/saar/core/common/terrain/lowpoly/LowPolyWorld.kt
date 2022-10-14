package org.saar.core.common.terrain.lowpoly

import org.joml.Vector2ic
import org.saar.core.common.r3d.DeferredRenderer3D
import org.saar.core.common.r3d.Renderer3D
import org.saar.core.common.terrain.World
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderNode
import org.saar.core.renderer.forward.ForwardRenderNode
import org.saar.core.renderer.shadow.ShadowsRenderNode

class LowPolyWorld(private val factory: LowPolyTerrainFactory) : World,
    ForwardRenderNode, DeferredRenderNode, ShadowsRenderNode {

    private val _children: MutableList<LowPolyTerrain> = mutableListOf()
    override val children: List<LowPolyTerrain> = this._children

    override fun getHeight(x: Float, z: Float): Float {
        return this.children.find { it.contains(x, z) }?.getHeight(x, z) ?: 0f
    }

    fun createTerrain(position: Vector2ic) {
        val terrain = this.factory.create(position)

        this._children.add(terrain)
    }

    override fun renderForward(context: RenderContext) {
        val models = this.children.map { it.model }
        Renderer3D.render(context, models)
    }

    override fun renderDeferred(context: RenderContext) {
        val models = this.children.map { it.model }
        DeferredRenderer3D.render(context, models)
    }

    override fun renderShadows(context: RenderContext) {
        val models = this.children.map { it.model }
        DeferredRenderer3D.render(context, models)
    }
}