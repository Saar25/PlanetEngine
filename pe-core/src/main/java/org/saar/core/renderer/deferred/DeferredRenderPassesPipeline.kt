package org.saar.core.renderer.deferred

import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.stencil.*

class DeferredRenderPassesPipeline(private vararg val renderPasses: DeferredRenderPass) {

    private val stencilState = StencilState(StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF), StencilMask.UNCHANGED)

    fun process(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        this.renderPasses.forEach { renderPass ->
            StencilTest.apply(this.stencilState)
            DepthTest.disable()

            renderPass.render(context, buffers)
        }
    }

    fun delete() = this.renderPasses.forEach { it.delete() }
}