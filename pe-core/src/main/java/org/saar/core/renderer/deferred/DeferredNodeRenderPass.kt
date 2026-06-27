package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderGraph
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.CullFaceRenderState
import org.saar.core.renderer.state.DepthTestRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screen
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.MutableTexture2D

fun RenderGraph.Builder.deferredNodePass(
    camera: ICamera, renderNode: DeferredRenderNode
): DeferredNodeRenderPass {
    val renderPass = DeferredNodeRenderPass(camera, renderNode)
    addPass(renderPass)
    return renderPass
}

fun RenderGraph.Builder.deferredNodePass(input: DeferredNodeRenderPass.Input.() -> Unit): DeferredNodeRenderPass.Output {
    val outputAlbedo = MutableTexture2D.create()
    val outputNormalSpecular = MutableTexture2D.create()
    val outputDepth = MutableTexture2D.create()
    val screen = buildScreen(width, height) {
        colorAttachment(outputAlbedo, InternalFormat.RGBA16F)
        colorAttachment(outputNormalSpecular, InternalFormat.RGBA16F)
        depthAttachment(outputDepth, InternalFormat.DEPTH24)
    }

    deferredNodePass(screen, input)

    return DeferredNodeRenderPass.Output(screen, outputAlbedo, outputNormalSpecular, outputDepth)
}

fun RenderGraph.Builder.deferredNodePass(screen: Screen, input: DeferredNodeRenderPass.Input.() -> Unit) {
    val input = DeferredNodeRenderPass.Input().apply(input)
    addPass(DeferredNodeRenderPass(screen, input))
}

@JvmName("create")
fun DeferredNodeRenderPass(camera: ICamera, vararg children: DeferredRenderNode): DeferredNodeRenderPass {
    val input = DeferredNodeRenderPass.Input().apply {
        this.camera = camera
        this.renderNode = DeferredRenderNodeGroup(*children)
    }
    return DeferredNodeRenderPass(null, input)
}

class DeferredNodeRenderPass(
    private val screen: Screen?,
    private val input: Input,
) : RenderPass {

    class Input {
        lateinit var camera: ICamera
        lateinit var renderNode: DeferredRenderNode
    }

    class Output(
        val screen: OffScreen,
        val albedo: MutableTexture2D,
        val normalSpecular: MutableTexture2D,
        val depth: MutableTexture2D,
    )

    override val renderState = CompositeRenderState(
        DepthTestRenderState(DepthState.WRITE),
        StencilTestRenderState(StencilState.ALWAYS_WRITE),
        CullFaceRenderState(CullFaceState.BACK_CCW),
    )

    override fun render(context: RenderContext) {
        this.screen?.setAsDraw()
        this.renderState.apply()

        val deferredContext = DeferredRenderContext(context, this.input.camera)
        this.input.renderNode.renderDeferred(deferredContext)
    }

    override fun delete() = this.input.renderNode.delete()
}

fun DeferredRenderNode.asDeferredRenderPass(camera: ICamera) = DeferredNodeRenderPass(camera, this)