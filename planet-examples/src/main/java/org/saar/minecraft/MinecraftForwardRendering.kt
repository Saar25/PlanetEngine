package org.saar.minecraft

import org.saar.core.fog.Fog
import org.saar.core.fog.FogDistance
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.renderer.RenderPipeline
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.forward.asForwardRenderNode
import org.saar.core.renderer.forward.passes.FogRenderPass
import org.saar.core.renderer.onto
import org.saar.core.renderer.renderpass.asRenderNode
import org.saar.core.screen.MainScreen
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.assureSize
import org.saar.core.screen.clear
import org.saar.gui.UIDisplay
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.utils.Vector3
import org.saar.minecraft.chunk.ChunkRenderNode
import org.saar.minecraft.chunk.WaterRenderNode

class MinecraftForwardRendering(
    private val uiDisplay: UIDisplay,
    private val world: World,
    private val radius: Int,
) : MinecraftRendering {

    private lateinit var screen1: OffScreen
    private lateinit var screen2: OffScreen

    override fun buildRenderPipeline(): RenderPipeline {
        val fog = Fog(
            Vector3.of(.0f, .5f, .7f),
            (this.radius * 15).toFloat(),
            (this.radius * 16).toFloat()
        )

        val prototype1 = DeferredScreenPrototype()
        screen1 = prototype1.toScreen(Fbo.create(1200, 700))

        val prototype2 = DeferredScreenPrototype()
        screen2 = prototype2.toScreen(Fbo.create(1200, 700))

        return RenderPipeline(
            ChunkRenderNode(this.world)
                .asForwardRenderNode().onto(screen1),
            WaterRenderNode(this.world)
                .asForwardRenderNode().onto(screen1),
            /*UnderwaterPostProcessor(this.world)
                .asRenderNode(prototype1.buffers).onto(screen2),*/
            /*FogRenderPass(fog, FogDistance.XZ)
                .asRenderNode(prototype1.buffers).onto(screen2),*/
            this.uiDisplay
                .asForwardRenderNode().onto(screen1),
            FxaaPostProcessor()
                .asRenderNode(prototype1.buffers).onto(MainScreen)
        )
    }

    override fun update() {
        this.screen1.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        this.screen1.assureSize(MainScreen.width, MainScreen.height)
        this.screen2.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        this.screen2.assureSize(MainScreen.width, MainScreen.height)
        MainScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
    }
}