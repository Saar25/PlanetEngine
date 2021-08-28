package org.saar.core.renderer.renderpass

import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.stencil.*

class RenderPassesPipelineHelper<T : RenderPass>(val renderPasses: Array<T>) {

    val stencilState = StencilState(StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.NOT_EQUAL, 0, 0xFF), StencilMask.UNCHANGED)

    inline fun process(renderCallback: (T) -> Unit) {
        this.renderPasses.forEach {
            StencilTest.apply(this.stencilState)
            DepthTest.disable()

            renderCallback(it)
        }
    }

    fun delete() = this.renderPasses.forEach { it.delete() }
}