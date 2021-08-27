package org.saar.core.renderer.deferred

import org.saar.core.camera.ICamera
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassScreenPrototype
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screens
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.fbos.FboBlitFilter
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class DeferredRenderPassesPipeline(private vararg val renderPasses: DeferredRenderPass) {

    private val prototype = RenderPassScreenPrototype()

    private val screen: OffScreen = Screens.fromPrototype(this.prototype, Fbo.create(0, 0))

    private val colourTexture: Texture
        get() = this.prototype.colourTexture

    private val stencilState = StencilState(StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF), StencilMask.UNCHANGED)

    fun process(camera: ICamera, inputBuffers: DeferredRenderingBuffers, screen: OffScreen): DeferredRenderingOutput {
        this.screen.assureSize(screen.width, screen.height)
        screen.copyTo(this.screen, FboBlitFilter.NEAREST, GlBuffer.DEPTH, GlBuffer.STENCIL)

        this.screen.setAsDraw()
        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH)

        val context = RenderPassContext(camera)

        val outputBuffers = runPipeline(inputBuffers, context)

        return DeferredRenderingOutput(this.screen, outputBuffers)
    }

    private fun runPipeline(input: DeferredRenderingBuffers, context: RenderPassContext): DeferredRenderingBuffers {
        return this.renderPasses.fold(input) { buffers, renderPass ->
            StencilTest.apply(this.stencilState)
            DepthTest.disable()

            renderPass.render(context, buffers)

            DeferredRenderingBuffers(this.colourTexture,
                buffers.normal, buffers.specular, buffers.depth)
        }
    }

    fun delete() {
        this.screen.delete()
        this.renderPasses.forEach { it.delete() }
    }
}