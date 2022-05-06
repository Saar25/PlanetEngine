package org.saar.minecraft

import org.saar.core.camera.Camera
import org.saar.core.fog.Fog
import org.saar.core.fog.FogDistance
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.renderer.forward.ForwardRenderNodeGroup
import org.saar.core.renderer.forward.ForwardRenderingPath
import org.saar.core.renderer.forward.ForwardRenderingPipeline
import org.saar.core.renderer.forward.passes.FogRenderPass
import org.saar.core.renderer.forward.passes.ForwardGeometryPass
import org.saar.core.renderer.p2d.GeometryPass2D
import org.saar.gui.UIDisplay
import org.saar.maths.utils.Vector3
import org.saar.minecraft.chunk.ChunkRenderNode
import org.saar.minecraft.chunk.WaterRenderNode
import org.saar.minecraft.postprocessors.UnderwaterPostProcessor

class MinecraftForwardRendering(
    private val uiDisplay: UIDisplay,
    private val world: World,
    private val camera: Camera,
    private val radius: Int,
) : MinecraftRendering {

    override fun buildRenderingPath(): ForwardRenderingPath {
        val renderNode = ForwardRenderNodeGroup(
            ChunkRenderNode(this.world),
            WaterRenderNode(this.world)
        )

        val fog = Fog(Vector3.of(.0f, .5f, .7f),
            (this.radius * 15).toFloat(),
            (this.radius * 16).toFloat())

        val pipeline = ForwardRenderingPipeline(
            ForwardGeometryPass(renderNode),
            UnderwaterPostProcessor(this.world),
            FogRenderPass(fog, FogDistance.XZ),
            GeometryPass2D(this.uiDisplay),
            FxaaPostProcessor()
        )

        return ForwardRenderingPath(this.camera, pipeline)
    }

    override fun update() = Unit
}