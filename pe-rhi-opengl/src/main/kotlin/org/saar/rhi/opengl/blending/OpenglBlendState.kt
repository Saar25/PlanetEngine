package org.saar.rhi.opengl.blending

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL33
import org.saar.rhi.blending.*

fun BlendState.toOpengl() = OpenglBlendState(this)

class OpenglBlendState(private val blendState: BlendState) {

    fun set() {
        // TODO: implement for multiple render targets
        val attachment = this.blendState.attachments.firstOrNull()

        if (attachment == null) {
            GL11.glDisable(GL11.GL_BLEND)
            GL11.glColorMask(true, true, true, true)
        } else {
            setAttachment(attachment)
        }

        setLogicOp()
        setBlendConstants()
    }

    private fun setAttachment(attachment: BlendAttachmentState) {
        val blendEnable = attachment.blendEnable ?: false

        if (blendEnable) {
            GL11.glEnable(GL11.GL_BLEND)
            GL14.glBlendFuncSeparate(
                (attachment.srcColorFactor ?: BlendFactor.ONE).glValue,
                (attachment.dstColorFactor ?: BlendFactor.ZERO).glValue,
                (attachment.srcAlphaFactor ?: BlendFactor.ONE).glValue,
                (attachment.dstAlphaFactor ?: BlendFactor.ZERO).glValue,
            )
            GL20.glBlendEquationSeparate(
                (attachment.colorBlendOp ?: BlendOp.ADD).glValue,
                (attachment.alphaBlendOp ?: BlendOp.ADD).glValue,
            )
        } else {
            GL11.glDisable(GL11.GL_BLEND)
        }

        val mask = attachment.colorWriteMask ?: 0xF
        GL11.glColorMask(
            (mask and 0x1) != 0,
            (mask and 0x2) != 0,
            (mask and 0x4) != 0,
            (mask and 0x8) != 0,
        )
    }

    private fun setLogicOp() {
        if (this.blendState.logicOpEnable ?: false) {
            GL11.glEnable(GL11.GL_COLOR_LOGIC_OP)
            GL11.glLogicOp(
                (this.blendState.logicOp ?: LogicOp.COPY).glValue
            )
        } else {
            GL11.glDisable(GL11.GL_COLOR_LOGIC_OP)
        }
    }

    private fun setBlendConstants() {
        this.blendState.blendConstants?.let { c ->
            GL14.glBlendColor(c.r, c.g, c.b, c.a)
        }
    }
}

private val BlendFactor.glValue
    get() = when (this) {
        BlendFactor.ZERO -> GL11.GL_ZERO
        BlendFactor.ONE -> GL11.GL_ONE
        BlendFactor.SRC_COLOR -> GL11.GL_SRC_COLOR
        BlendFactor.ONE_MINUS_SRC_COLOR -> GL11.GL_ONE_MINUS_SRC_COLOR
        BlendFactor.DST_COLOR -> GL11.GL_DST_COLOR
        BlendFactor.ONE_MINUS_DST_COLOR -> GL11.GL_ONE_MINUS_DST_COLOR
        BlendFactor.SRC_ALPHA -> GL11.GL_SRC_ALPHA
        BlendFactor.ONE_MINUS_SRC_ALPHA -> GL11.GL_ONE_MINUS_SRC_ALPHA
        BlendFactor.DST_ALPHA -> GL11.GL_DST_ALPHA
        BlendFactor.ONE_MINUS_DST_ALPHA -> GL11.GL_ONE_MINUS_DST_ALPHA
        BlendFactor.CONSTANT_COLOR -> GL14.GL_CONSTANT_COLOR
        BlendFactor.ONE_MINUS_CONSTANT_COLOR -> GL14.GL_ONE_MINUS_CONSTANT_COLOR
        BlendFactor.CONSTANT_ALPHA -> GL14.GL_CONSTANT_ALPHA
        BlendFactor.ONE_MINUS_CONSTANT_ALPHA -> GL14.GL_ONE_MINUS_CONSTANT_ALPHA
        BlendFactor.SRC_ALPHA_SATURATE -> GL11.GL_SRC_ALPHA_SATURATE
        BlendFactor.SRC1_COLOR -> GL33.GL_SRC1_COLOR
        BlendFactor.ONE_MINUS_SRC1_COLOR -> GL33.GL_ONE_MINUS_SRC1_COLOR
        BlendFactor.SRC1_ALPHA -> GL33.GL_SRC1_ALPHA
        BlendFactor.ONE_MINUS_SRC1_ALPHA -> GL33.GL_ONE_MINUS_SRC1_ALPHA
    }

private val BlendOp.glValue
    get() = when (this) {
        BlendOp.ADD -> GL14.GL_FUNC_ADD
        BlendOp.SUBTRACT -> GL14.GL_FUNC_SUBTRACT
        BlendOp.REVERSE_SUBTRACT -> GL14.GL_FUNC_REVERSE_SUBTRACT
        BlendOp.MIN -> GL14.GL_MIN
        BlendOp.MAX -> GL14.GL_MAX
    }

private val LogicOp.glValue
    get() = when (this) {
        LogicOp.CLEAR -> GL11.GL_CLEAR
        LogicOp.AND -> GL11.GL_AND
        LogicOp.AND_REVERSE -> GL11.GL_AND_REVERSE
        LogicOp.COPY -> GL11.GL_COPY
        LogicOp.AND_INVERTED -> GL11.GL_AND_INVERTED
        LogicOp.NO_OP -> GL11.GL_NOOP
        LogicOp.XOR -> GL11.GL_XOR
        LogicOp.OR -> GL11.GL_OR
        LogicOp.NOR -> GL11.GL_NOR
        LogicOp.EQUIVALENT -> GL11.GL_EQUIV
        LogicOp.INVERT -> GL11.GL_INVERT
        LogicOp.OR_REVERSE -> GL11.GL_OR_REVERSE
        LogicOp.COPY_INVERTED -> GL11.GL_COPY_INVERTED
        LogicOp.OR_INVERTED -> GL11.GL_OR_INVERTED
        LogicOp.NAND -> GL11.GL_NAND
        LogicOp.SET -> GL11.GL_SET
    }
