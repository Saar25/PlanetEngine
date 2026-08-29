package org.saar.rhi.opengl.rasterization

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL32
import org.saar.rhi.rasterization.CullMode
import org.saar.rhi.rasterization.FrontFace
import org.saar.rhi.rasterization.PolygonMode
import org.saar.rhi.rasterization.RasterizationState

fun RasterizationState.toOpengl() = OpenglRasterizationState(this)

class OpenglRasterizationState(rasterizationState: RasterizationState) {

    private val cullMode = rasterizationState.cullMode ?: CullMode.BACK
    private val polygonMode = rasterizationState.polygonMode ?: PolygonMode.FILL
    private val frontFace = rasterizationState.frontFace ?: FrontFace.COUNTER_CLOCKWISE
    private val lineWidth = rasterizationState.lineWidth ?: 1.0f

    fun set() {
        GL32.glProvokingVertex(GL32.GL_FIRST_VERTEX_CONVENTION) // Opengl default is last, while Vulkan is first

        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, this.polygonMode.glValue)

        if (this.cullMode == CullMode.NONE) {
            GL11.glDisable(GL11.GL_CULL_FACE)
        } else {
            GL11.glEnable(GL11.GL_CULL_FACE)
            GL11.glCullFace(this.cullMode.glValue)
        }
        GL11.glFrontFace(this.frontFace.glValue)
        GL11.glLineWidth(this.lineWidth)
    }
}

private val PolygonMode.glValue
    get() = when (this) {
        PolygonMode.POINT -> GL11.GL_POINT
        PolygonMode.LINE -> GL11.GL_LINE
        PolygonMode.FILL -> GL11.GL_FILL
    }

private val CullMode.glValue
    get() = when (this) {
        CullMode.NONE -> GL11.GL_NONE
        CullMode.FRONT -> GL11.GL_FRONT
        CullMode.BACK -> GL11.GL_BACK
        CullMode.FRONT_AND_BACK -> GL11.GL_FRONT_AND_BACK
    }

private val FrontFace.glValue
    get() = when (this) {
        FrontFace.COUNTER_CLOCKWISE -> GL11.GL_CCW
        FrontFace.CLOCKWISE -> GL11.GL_CW
    }