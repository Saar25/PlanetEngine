package org.saar.core.renderer

import org.saar.core.camera.ICamera
import org.saar.core.renderer.renderpass.RenderPassBuffers
import org.saar.core.screen.Screens
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
    private val prototype: RenderingPathScreenPrototype<T>,
) : RenderingPath<T> {

    private val stencilState = StencilState(
        StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF),
        StencilMask.UNCHANGED
    )

    private val screenA = Screens.fromPrototype(this.prototype, Fbo.create(0, 0), SimpleAllocationStrategy())
    private val screenB = Screens.fromPrototype(this.prototype, Fbo.create(0, 0), SimpleAllocationStrategy())
    private val swapScreen = mapOf(screenA to screenB, screenB to screenA)

    override fun render(): RenderingOutput<T> {
        this.screenA.resizeToMainScreen()
        this.screenB.resizeToMainScreen()

        this.screenA.setAsDraw()
        DepthTest.apply(mask = DepthMask.WRITE)
        StencilTest.apply(mask = StencilMask.UNCHANGED)
        GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH, GlBuffer.STENCIL)

        var currentScreen = this.screenA

        val context = RenderContext(this.camera)
        this.pipeline.passes.forEach {
            StencilTest.apply(this.stencilState)
            DepthTest.disable()
            BlendTest.disable()

            it.prepare(context, this.prototype.buffers)
            currentScreen.setAsDraw()
            it.render(context, this.prototype.buffers)

            currentScreen = swapScreen[currentScreen]!!
        }

        return RenderingOutput(this.screenA, this.prototype.buffers)
    }

    override fun delete() {
        this.screenA.delete()
        this.pipeline.passes.forEach { it.delete() }
    }
}