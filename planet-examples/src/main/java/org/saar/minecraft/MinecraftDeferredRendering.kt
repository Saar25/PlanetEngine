package org.saar.minecraft

import org.saar.core.camera.Camera
import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.camera.projection.SimpleOrthographicProjection
import org.saar.core.fog.Fog
import org.saar.core.fog.FogDistance
import org.saar.core.light.DirectionalLight
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredRenderingPath
import org.saar.core.renderer.deferred.DeferredRenderingPipeline
import org.saar.core.renderer.deferred.passes.DeferredGeometryPass
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass
import org.saar.core.renderer.forward.passes.FogRenderPass
import org.saar.core.renderer.p2d.GeometryPass2D
import org.saar.core.renderer.shadow.ShadowsQuality
import org.saar.core.renderer.shadow.ShadowsRenderingPath
import org.saar.gui.UIBlock
import org.saar.gui.UIDisplay
import org.saar.maths.utils.Vector3
import org.saar.minecraft.chunk.ChunkRenderNode
import org.saar.minecraft.chunk.WaterRenderNode
import org.saar.minecraft.postprocessors.UnderwaterPostProcessor

class MinecraftDeferredRendering(
    private val uiDisplay: UIDisplay,
    private val world: World,
    private val camera: Camera,
    private val radius: Int,
) : MinecraftRendering {

    private var shadowsRenderingPath: ShadowsRenderingPath? = null

    override fun buildRenderingPath(): DeferredRenderingPath {
        val renderNode = DeferredRenderNodeGroup(
            ChunkRenderNode(this.world),
            WaterRenderNode(this.world)
        )

        val sun = DirectionalLight()
        sun.direction.set(-.3f, -1f, -.7f)
        sun.colour.set(1f, 1f, 1f)

        val a = 50f
        val shadowsProjection: OrthographicProjection =
            SimpleOrthographicProjection(-a, a, -a, a, -a, a)

        this.shadowsRenderingPath = ShadowsRenderingPath(
            ShadowsQuality.HIGH, shadowsProjection, sun, ChunkRenderNode(this.world)
        )
        val shadowMap = this.shadowsRenderingPath!!.render().buffers.depth

        val uiShadowMap = UIBlock()
        uiShadowMap.style.backgroundImage.set(shadowMap)
        uiShadowMap.style.width.set(200)
        uiShadowMap.style.height.set(200)
        this.uiDisplay.add(uiShadowMap)

        val fog = Fog(Vector3.of(.0f, .5f, .7f),
            (this.radius * 15).toFloat(),
            (this.radius * 16).toFloat())

        val pipeline = DeferredRenderingPipeline(
            DeferredGeometryPass(renderNode),
            ShadowsRenderPass(this.shadowsRenderingPath!!.camera, shadowMap, sun),
            UnderwaterPostProcessor(this.world),
            FogRenderPass(fog, FogDistance.XZ),
            GeometryPass2D(this.uiDisplay),
            FxaaPostProcessor()
        )

        return DeferredRenderingPath(this.camera, pipeline)
    }

    override fun update() {
        this.shadowsRenderingPath?.camera?.transform?.position?.set(this.camera.transform.position)
        this.shadowsRenderingPath?.render()
    }
}