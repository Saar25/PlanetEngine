package org.saar.rhi.opengl.renderpass

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import org.saar.rhi.renderpass.LoadOp
import org.saar.rhi.renderpass.RenderPass

fun RenderPass.toOpengl(fbo: Int = 0, width: Int = 0, height: Int = 0) =
    OpenglRenderPass(this, fbo, width, height)

class OpenglRenderPass(
    private val renderPass: RenderPass,
    private val fbo: Int,
    private val width: Int,
    private val height: Int,
) {

    fun begin() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, this.fbo)
        GL11.glViewport(0, 0, this.width, this.height)
        clearAttachments()
        setDrawBuffers()
    }

    fun end() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0)
    }

    private fun clearAttachments() {
        var mask = 0

        for (attachment in this.renderPass.colorAttachments) {
            if (attachment.loadOp == LoadOp.CLEAR) {
                val (r, g, b, a) = attachment.clearColor
                GL11.glClearColor(r, g, b, a)
                mask = mask or GL11.GL_COLOR_BUFFER_BIT
            }
        }

        this.renderPass.depthAttachment?.let { attachment ->
            if (attachment.loadOp == LoadOp.CLEAR) {
                GL11.glClearDepth(attachment.clearColor.depth.toDouble())
                mask = mask or GL11.GL_DEPTH_BUFFER_BIT
            }
            if (attachment.stencilLoadOp == LoadOp.CLEAR) {
                GL11.glClearStencil(attachment.clearColor.stencil)
                mask = mask or GL11.GL_STENCIL_BUFFER_BIT
            }
        }

        if (mask != 0) {
            GL11.glClear(mask)
        }
    }

    private fun setDrawBuffers() {
        val buffers = if (this.renderPass.colorAttachments.isEmpty()) {
            intArrayOf(GL11.GL_NONE)
        } else {
            IntArray(this.renderPass.colorAttachments.size) { i -> GL30.GL_COLOR_ATTACHMENT0 + i }
        }

        GL30.glDrawBuffers(buffers)
    }
}
