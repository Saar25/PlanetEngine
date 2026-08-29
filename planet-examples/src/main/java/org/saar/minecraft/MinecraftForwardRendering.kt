package org.saar.minecraft

import org.saar.core.camera.Camera
import org.saar.core.common.renderpass.fxaaPass
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.renderGraph
import org.saar.core.screen.MainScreen
import org.saar.core.screen.buildScreen
import org.saar.gui.UIDisplay
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.texture.MutableTexture2D

private const val WIDTH = 1200
private const val HEIGHT = 700

class MinecraftForwardRendering(
    private val uiDisplay: UIDisplay,
    private val world: World,
    private val camera: Camera,
    private val radius: Int,
) : MinecraftRendering {

    override fun buildRenderGraph(): RenderGraph = renderGraph(WIDTH, HEIGHT) {
        val albedo = MutableTexture2D.create()
        val normalSpecular = MutableTexture2D.create()
        val screen = buildScreen(WIDTH, HEIGHT) {
            colorAttachment(albedo, InternalFormat.RGBA16F)
            colorAttachment(normalSpecular, InternalFormat.RGBA16F)
            depthAttachment(MutableTexture2D.create(), InternalFormat.DEPTH24)
        }

        val camera = this@MinecraftForwardRendering.camera
        addPass(ForwardNodeScreenPass(screen, camera, WorldForwardNode(this@MinecraftForwardRendering.world), true))
        fxaaPass(MainScreen) {
            this.albedoBuffer = albedo
        }
        addPass(UIRenderPass(this@MinecraftForwardRendering.uiDisplay))
    }

    override fun update() = Unit
}
