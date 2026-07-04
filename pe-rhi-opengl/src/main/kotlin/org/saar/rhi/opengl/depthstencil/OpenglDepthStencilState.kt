package org.saar.rhi.opengl.depthstencil

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import org.lwjgl.opengl.GL20
import org.saar.rhi.depthstencil.CompareOp
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.depthstencil.StencilOp
import org.saar.rhi.depthstencil.StencilOpState

fun DepthStencilState.toOpengl() = OpenglDepthStencilState(this)

class OpenglDepthStencilState(depthStencilState: DepthStencilState) {
    private val depthTestEnable = depthStencilState.depthTestEnable ?: false
    private val depthWriteEnable = depthStencilState.depthWriteEnable ?: true
    private val depthCompareOp = depthStencilState.depthCompareOp ?: CompareOp.LESS
    private val stencilTestEnable = depthStencilState.stencilTestEnable ?: false
    private val front = depthStencilState.front?.coalesce()
        ?: StencilOpStateCoalesced()
    private val back = depthStencilState.back?.coalesce()
        ?: StencilOpStateCoalesced()

    fun set() {
        setDepth()
        setStencil()
    }

    private fun setDepth() {
        if (this.depthTestEnable) {
            GL11.glEnable(GL11.GL_DEPTH_TEST)
        } else {
            GL11.glDisable(GL11.GL_DEPTH_TEST)
        }
        GL11.glDepthMask(this.depthWriteEnable)
        GL11.glDepthFunc(this.depthCompareOp.glValue)
    }

    private fun setStencil() {
        if (this.stencilTestEnable) {
            GL11.glEnable(GL11.GL_STENCIL_TEST)
        } else {
            GL11.glDisable(GL11.GL_STENCIL_TEST)
            return
        }
        setStencilFace(GL11.GL_FRONT, this.front)
        setStencilFace(GL11.GL_BACK, this.back)
    }

    private fun setStencilFace(face: Int, s: StencilOpStateCoalesced) {
        GL20.glStencilFuncSeparate(face, s.compareOp.glValue, s.reference, s.compareMask)
        GL20.glStencilOpSeparate(face, s.failOp.glValue, s.depthFailOp.glValue, s.passOp.glValue)
        GL20.glStencilMaskSeparate(face, s.writeMask)
    }

    private data class StencilOpStateCoalesced(
        val failOp: StencilOp = StencilOp.KEEP,
        val passOp: StencilOp = StencilOp.KEEP,
        val depthFailOp: StencilOp = StencilOp.KEEP,
        val compareOp: CompareOp = CompareOp.ALWAYS,
        val compareMask: Int = -1,
        val writeMask: Int = -1,
        val reference: Int = 0,
    )

    private fun StencilOpState.coalesce() = StencilOpStateCoalesced(
        failOp = this.failOp ?: StencilOp.KEEP,
        passOp = this.passOp ?: StencilOp.KEEP,
        depthFailOp = this.depthFailOp ?: StencilOp.KEEP,
        compareOp = this.compareOp ?: CompareOp.ALWAYS,
        compareMask = this.compareMask ?: -1,
        writeMask = this.writeMask ?: -1,
        reference = this.reference ?: 0,
    )
}

private val CompareOp.glValue
    get() = when (this) {
        CompareOp.NEVER -> GL11.GL_NEVER
        CompareOp.LESS -> GL11.GL_LESS
        CompareOp.EQUAL -> GL11.GL_EQUAL
        CompareOp.LESS_OR_EQUAL -> GL11.GL_LEQUAL
        CompareOp.GREATER -> GL11.GL_GREATER
        CompareOp.NOT_EQUAL -> GL11.GL_NOTEQUAL
        CompareOp.GREATER_OR_EQUAL -> GL11.GL_GEQUAL
        CompareOp.ALWAYS -> GL11.GL_ALWAYS
    }

private val StencilOp.glValue
    get() = when (this) {
        StencilOp.KEEP -> GL11.GL_KEEP
        StencilOp.ZERO -> GL11.GL_ZERO
        StencilOp.REPLACE -> GL11.GL_REPLACE
        StencilOp.INCREMENT_AND_CLAMP -> GL11.GL_INCR
        StencilOp.DECREMENT_AND_CLAMP -> GL11.GL_DECR
        StencilOp.INVERT -> GL11.GL_INVERT
        StencilOp.INCREMENT_AND_WRAP -> GL14.GL_INCR_WRAP
        StencilOp.DECREMENT_AND_WRAP -> GL14.GL_DECR_WRAP
    }
