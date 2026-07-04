package org.saar.rhi.opengl.viewport

import org.lwjgl.opengl.GL11
import org.saar.rhi.viewport.Scissor
import org.saar.rhi.viewport.Viewport
import org.saar.rhi.viewport.ViewportState

fun ViewportState.toOpengl() = OpenglViewportState(this)

// TODO: use this class instead of manually calling glViewport, also make sure this can be dynamic
class OpenglViewportState(private val viewportState: ViewportState) {

    fun set() {
        this.viewportState.viewports.firstOrNull()?.let(::setViewport)
        this.viewportState.scissors.firstOrNull().let(::setScissors)
    }

    private fun setViewport(viewport: Viewport) {
        val (x, y, width, height, minDepth, maxDepth) = viewport
        GL11.glViewport(x.toInt(), y.toInt(), width.toInt(), height.toInt())
        // TODO: convert whole project to vulkan [0f, 1f] depth range
        GL11.glDepthRange(minDepth?.toDouble() ?: -1.0, maxDepth?.toDouble() ?: 1.0)
    }

    private fun setScissors(scissor: Scissor?) {
        if (scissor == null) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST)
        } else {
            GL11.glEnable(GL11.GL_SCISSOR_TEST)
            val (x, y, width, height) = scissor
            GL11.glScissor(x, y, width, height)
        }
    }
}
