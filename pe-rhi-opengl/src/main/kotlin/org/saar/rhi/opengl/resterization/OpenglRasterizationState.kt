package org.saar.rhi.opengl.resterization

import org.lwjgl.opengl.GL11
import org.saar.rhi.resterization.CullMode
import org.saar.rhi.resterization.FrontFace
import org.saar.rhi.resterization.PolygonMode
import org.saar.rhi.resterization.RasterizationState

private val defaults = RasterizationState(
    cullMode = CullMode.BACK,
    polygonMode = PolygonMode.FILL,
    frontFace = FrontFace.COUNTER_CLOCKWISE,
    lineWidth = 1.0f,
)

fun RasterizationState.toOpengl() = OpenglRasterizationState(this)

class OpenglRasterizationState(private val rasterizationState: RasterizationState) {

    private val cullMode = this.rasterizationState.cullMode ?: defaults.cullMode!!
    private val polygonMode = this.rasterizationState.polygonMode ?: defaults.polygonMode!!
    private val frontFace = this.rasterizationState.frontFace ?: defaults.frontFace!!
    private val lineWidth = this.rasterizationState.lineWidth ?: defaults.lineWidth!!

    fun set() {
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