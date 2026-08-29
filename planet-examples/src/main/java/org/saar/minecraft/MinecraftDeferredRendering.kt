package org.saar.minecraft

import org.saar.core.camera.Camera
import org.saar.core.common.renderpass.fogPass
import org.saar.core.common.renderpass.fxaaPass
import org.saar.core.common.renderpass.lightPass
import org.saar.core.fog.Fog
import org.saar.core.fog.FogDistance
import org.saar.core.light.DirectionalLight
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.deferredNodePass
import org.saar.core.renderer.renderGraph
import org.saar.core.screen.MainScreen
import org.saar.gui.UIDisplay
import org.saar.maths.utils.Vector3
import org.saar.minecraft.chunk.ChunkRenderNode
import org.saar.minecraft.chunk.WaterRenderNode

private const val WIDTH = 1200
private const val HEIGHT = 700

class MinecraftDeferredRendering(
    private val uiDisplay: UIDisplay,
    private val world: World,
    private val camera: Camera,
    private val radius: Int,
) : MinecraftRendering {

    override fun buildRenderGraph(): RenderGraph = renderGraph(WIDTH, HEIGHT) {
        val sun = DirectionalLight().apply {
            direction.set(-.3f, -1f, -.7f)
            color.set(1f, 1f, 1f)
        }

        val fog = Fog(
            Vector3.of(.0f, .5f, .7f),
            (this@MinecraftDeferredRendering.radius * 15).toFloat(),
            (this@MinecraftDeferredRendering.radius * 16).toFloat()
        )

        val camera = this@MinecraftDeferredRendering.camera
        val renderNode = DeferredRenderNodeGroup(
            ChunkRenderNode(this@MinecraftDeferredRendering.world),
            WaterRenderNode(this@MinecraftDeferredRendering.world)
        )

        val deferredOutput = deferredNodePass {
            this.camera = camera
            this.renderNode = renderNode
        }
        val lightOutput = lightPass {
            this.albedoBuffer = deferredOutput.albedo
            this.normalSpecularBuffer = deferredOutput.normalSpecular
            this.depthBuffer = deferredOutput.depth
            this.camera = camera
            this.directionalLights = arrayOf(sun)
        }
        val fogOutput = fogPass {
            this.albedoBuffer = lightOutput.albedo
            this.depthBuffer = deferredOutput.depth
            this.camera = camera
            this.fog = fog
            this.fogDistance = FogDistance.XZ
        }
        fxaaPass(MainScreen) {
            this.albedoBuffer = fogOutput.albedo
        }
        addPass(UIRenderPass(this@MinecraftDeferredRendering.uiDisplay))
    }

    override fun update() = Unit
}
