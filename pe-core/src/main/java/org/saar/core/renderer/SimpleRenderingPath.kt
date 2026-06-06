package org.saar.core.renderer

import org.saar.core.camera.ICamera
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.resizeToMainScreen
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.depth.DepthMask
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

class SimpleRenderingPath<T : RenderPassBuffers>(
    private val camera: ICamera,
    private val pipeline: RenderingPathPipeline<T>,
    private val prototypeA: RenderingPathScreenPrototype<T>,
    private val prototypeB: RenderingPathScreenPrototype<T>,
) : RenderingPath<T> {

    private val stencilState = StencilState(
        StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF),
        StencilMask.UNCHANGED
    )

    private val screenA = this.prototypeA.toScreen(Fbo.create(0, 0), SimpleAllocationStrategy())
    private val screenB = this.prototypeB.toScreen(Fbo.create(0, 0), SimpleAllocationStrategy())
    private val swapPrototype = mapOf(prototypeA to prototypeB, prototypeB to prototypeA)
    private val swapScreen = mapOf(screenA to screenB, screenB to screenA)

    override fun render(): RenderingOutput<T> {
        this.screenA.resizeToMainScreen()
        this.screenA.setAsDraw()
        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)
        this.screenB.resizeToMainScreen()
        this.screenB.setAsDraw()
        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        val context = RenderContext(this.camera)
        StencilTest.disable()
        DepthTest.disable()
        BlendTest.disable()

        var currentScreen = this.screenA
        var currentPrototype = this.prototypeB

        // TODO: if next pass ignores the previous output and does not repaint it, it is ignored
        this.pipeline.passes.forEach {
            currentScreen.setAsDraw()
            it.prepare(context, currentPrototype.buffers)
            it.render(context, currentPrototype.buffers)

            currentScreen = swapScreen[currentScreen]!!
            currentPrototype = swapPrototype[currentPrototype]!!
        }

        currentScreen = swapScreen[currentScreen]!!
        return RenderingOutput(currentScreen, currentPrototype.buffers)
    }

    override fun delete() {
        this.screenA.delete()
        this.screenB.delete()
        this.pipeline.passes.forEach { it.delete() }
    }
}