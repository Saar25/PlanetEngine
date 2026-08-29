package org.saar.core.renderer.shadow

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.RenderPass
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screen
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

fun RenderGraph.Builder.shadowsNodePass(
    quality: ShadowsQuality,
    input: ShadowsNodeRenderPass.Input.() -> Unit
): ShadowsNodeRenderPass.Output {
    val outputDepth = MutableTexture2D.create()
    val screen = buildScreen(quality.imageSize, quality.imageSize) {
        depthAttachment(outputDepth, InternalFormat.DEPTH24)
    }

    val input = ShadowsNodeRenderPass.Input().apply(input)
    addPass(ShadowsNodeRenderPass(screen, input))

    return ShadowsNodeRenderPass.Output(screen, outputDepth)
}

fun ShadowsNodeRenderPass(
    camera: ICamera,
    renderNode: ShadowsRenderNode
): ShadowsNodeRenderPass {
    val input = ShadowsNodeRenderPass.Input().apply {
        this.camera = camera
        this.renderNode = renderNode
    }
    return ShadowsNodeRenderPass(null, input)
}

class ShadowsNodeRenderPass(
    private val screen: Screen?,
    private val input: Input,
) : RenderPass {

    class Input {
        lateinit var camera: ICamera
        lateinit var renderNode: ShadowsRenderNode
    }

    class Output(val screen: OffScreen, val depth: MutableTexture2D)

    override fun render(context: RenderContext) {
        this.screen?.setAsDraw()
        GlUtils.clear(GlBuffer.DEPTH)

        val shadowsContext = ShadowsRenderContext(context, this.input.camera)
        this.input.renderNode.renderShadows(shadowsContext)
    }

    override fun delete() = this.input.renderNode.delete()
}

fun ShadowsRenderNode.asShadowsRenderPass(camera: ICamera) = ShadowsNodeRenderPass(camera, this)