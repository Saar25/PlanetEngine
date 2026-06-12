package org.saar.minecraft

import org.saar.core.camera.Camera
import org.saar.core.camera.projection.SimpleOrthographicProjection
import org.saar.core.fog.Fog
import org.saar.core.light.DirectionalLight
import org.saar.core.postprocessing.processors.FxaaPostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPipeline
import org.saar.core.renderer.deferred.DeferredRenderNodeGroup
import org.saar.core.renderer.deferred.DeferredScreenPrototype
import org.saar.core.renderer.deferred.asDeferredRenderNode
import org.saar.core.renderer.deferred.passes.ShadowsRenderPass
import org.saar.core.renderer.forward.asForwardRenderNode
import org.saar.core.renderer.onto
import org.saar.core.renderer.renderpass.asRenderNode
import org.saar.core.renderer.shadow.ShadowsCamera
import org.saar.core.renderer.shadow.ShadowsQuality
import org.saar.core.renderer.shadow.ShadowsScreenPrototype
import org.saar.core.renderer.shadow.asShadowsRenderNode
import org.saar.core.screen.MainScreen
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.assureSize
import org.saar.core.screen.clear
import org.saar.gui.UIBlock
import org.saar.gui.UIDisplay
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.maths.utils.Vector3
import org.saar.minecraft.chunk.ChunkRenderNode
import org.saar.minecraft.chunk.WaterRenderNode

class MinecraftDeferredRendering(
    private val uiDisplay: UIDisplay,
    private val world: World,
    private val camera: Camera,
    private val radius: Int,
) : MinecraftRendering {

    private lateinit var shadowsScreen: OffScreen
    private lateinit var screen1: OffScreen
    private lateinit var screen2: OffScreen

    private lateinit var shadowsCamera: ShadowsCamera
    private lateinit var shadowsRenderPipeline: RenderPipeline

    override fun buildRenderPipeline(): RenderPipeline {
        val renderNode = DeferredRenderNodeGroup(
            ChunkRenderNode(this.world),
            WaterRenderNode(this.world)
        )

        val sun = DirectionalLight()
        sun.direction.set(-.3f, -1f, -.7f)
        sun.colour.set(1f, 1f, 1f)

        val shadowsProjection = 50f.let { SimpleOrthographicProjection(-it, it, -it, it, -it, it) }
        this.shadowsCamera = ShadowsCamera(shadowsProjection, sun)

        val shadowsPrototype = ShadowsScreenPrototype()
        val fbo = Fbo.create(ShadowsQuality.HIGH.imageSize, ShadowsQuality.HIGH.imageSize)
        this.shadowsScreen = shadowsPrototype.toScreen(fbo)

        this.shadowsRenderPipeline = RenderPipeline(
            ChunkRenderNode(this.world)
                .asShadowsRenderNode().onto(shadowsScreen),
        )

        val shadowMap = shadowsPrototype.buffers.depth

        val uiShadowMap = UIBlock()
        uiShadowMap.style.backgroundImage.set(shadowMap)
        uiShadowMap.style.width.set(200)
        uiShadowMap.style.height.set(200)
        this.uiDisplay.add(uiShadowMap)

        val fog = Fog(
            Vector3.of(.0f, .5f, .7f),
            (this.radius * 15).toFloat(),
            (this.radius * 16).toFloat()
        )

        val prototype1 = DeferredScreenPrototype()
        this.screen1 = prototype1.toScreen(Fbo.create(1200, 700))

        val prototype2 = DeferredScreenPrototype()
        this.screen2 = prototype2.toScreen(Fbo.create(1200, 700))

        return RenderPipeline(
            renderNode
                .asDeferredRenderNode().onto(screen1),
            ShadowsRenderPass(shadowsCamera, shadowMap, sun)
                .asRenderNode(prototype1.buffers).onto(screen2),
            /*UnderwaterPostProcessor(this.world)
                .asRenderNode(prototype2.buffers).onto(screen1),
            FogRenderPass(fog, FogDistance.XZ)
                .asRenderNode(prototype1.buffers).onto(screen2),*/
            this.uiDisplay
                .asForwardRenderNode().onto(screen2),
            FxaaPostProcessor()
                .asRenderNode(prototype2.buffers).onto(MainScreen)
        )
    }

    override fun update() {
        this.shadowsScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        this.screen1.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        this.screen1.assureSize(MainScreen.width, MainScreen.height)
        this.screen2.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        this.screen2.assureSize(MainScreen.width, MainScreen.height)
        MainScreen.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        this.shadowsCamera.transform.position.set(this.camera.transform.position)
        this.shadowsRenderPipeline.render(RenderContext(shadowsCamera))
    }
}