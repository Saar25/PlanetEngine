package org.saar.rhi.vulkan.blending

import org.lwjgl.vulkan.VK10
import org.lwjgl.vulkan.VkPipelineColorBlendAttachmentState
import org.lwjgl.vulkan.VkPipelineColorBlendStateCreateInfo
import org.saar.rhi.blending.BlendFactor
import org.saar.rhi.blending.BlendOp
import org.saar.rhi.blending.BlendState
import org.saar.rhi.blending.LogicOp

fun BlendState.toVulkan(): VkPipelineColorBlendStateCreateInfo {
    val attachments = VkPipelineColorBlendAttachmentState.calloc(this.attachments.size)
    for ((i, a) in this.attachments.withIndex()) {
        attachments[i].apply {
            a.blendEnable?.let { blendEnable(it) }
            a.srcColorFactor?.let { srcColorBlendFactor(it.vkValue) }
            a.dstColorFactor?.let { dstColorBlendFactor(it.vkValue) }
            a.colorBlendOp?.let { colorBlendOp(it.vkValue) }
            a.srcAlphaFactor?.let { srcAlphaBlendFactor(it.vkValue) }
            a.dstAlphaFactor?.let { dstAlphaBlendFactor(it.vkValue) }
            a.alphaBlendOp?.let { alphaBlendOp(it.vkValue) }
            a.colorWriteMask?.let { colorWriteMask(it) }
        }
    }

    return VkPipelineColorBlendStateCreateInfo.calloc().apply {
        `sType$Default`()
        pAttachments(attachments)
        this@toVulkan.logicOpEnable?.let { logicOpEnable(it) }
        this@toVulkan.logicOp?.let { logicOp(it.vkValue) }
        this@toVulkan.blendConstants?.let { c ->
            blendConstants(0, c.r)
            blendConstants(1, c.g)
            blendConstants(2, c.b)
            blendConstants(3, c.a)
        }
    }
}

private val BlendFactor.vkValue
    get() = when (this) {
        BlendFactor.ZERO -> VK10.VK_BLEND_FACTOR_ZERO
        BlendFactor.ONE -> VK10.VK_BLEND_FACTOR_ONE
        BlendFactor.SRC_COLOR -> VK10.VK_BLEND_FACTOR_SRC_COLOR
        BlendFactor.ONE_MINUS_SRC_COLOR -> VK10.VK_BLEND_FACTOR_ONE_MINUS_SRC_COLOR
        BlendFactor.DST_COLOR -> VK10.VK_BLEND_FACTOR_DST_COLOR
        BlendFactor.ONE_MINUS_DST_COLOR -> VK10.VK_BLEND_FACTOR_ONE_MINUS_DST_COLOR
        BlendFactor.SRC_ALPHA -> VK10.VK_BLEND_FACTOR_SRC_ALPHA
        BlendFactor.ONE_MINUS_SRC_ALPHA -> VK10.VK_BLEND_FACTOR_ONE_MINUS_SRC_ALPHA
        BlendFactor.DST_ALPHA -> VK10.VK_BLEND_FACTOR_DST_ALPHA
        BlendFactor.ONE_MINUS_DST_ALPHA -> VK10.VK_BLEND_FACTOR_ONE_MINUS_DST_ALPHA
        BlendFactor.CONSTANT_COLOR -> VK10.VK_BLEND_FACTOR_CONSTANT_COLOR
        BlendFactor.ONE_MINUS_CONSTANT_COLOR -> VK10.VK_BLEND_FACTOR_ONE_MINUS_CONSTANT_COLOR
        BlendFactor.CONSTANT_ALPHA -> VK10.VK_BLEND_FACTOR_CONSTANT_ALPHA
        BlendFactor.ONE_MINUS_CONSTANT_ALPHA -> VK10.VK_BLEND_FACTOR_ONE_MINUS_CONSTANT_ALPHA
        BlendFactor.SRC_ALPHA_SATURATE -> VK10.VK_BLEND_FACTOR_SRC_ALPHA_SATURATE
        BlendFactor.SRC1_COLOR -> VK10.VK_BLEND_FACTOR_SRC1_COLOR
        BlendFactor.ONE_MINUS_SRC1_COLOR -> VK10.VK_BLEND_FACTOR_ONE_MINUS_SRC1_COLOR
        BlendFactor.SRC1_ALPHA -> VK10.VK_BLEND_FACTOR_SRC1_ALPHA
        BlendFactor.ONE_MINUS_SRC1_ALPHA -> VK10.VK_BLEND_FACTOR_ONE_MINUS_SRC1_ALPHA
    }

private val BlendOp.vkValue
    get() = when (this) {
        BlendOp.ADD -> VK10.VK_BLEND_OP_ADD
        BlendOp.SUBTRACT -> VK10.VK_BLEND_OP_SUBTRACT
        BlendOp.REVERSE_SUBTRACT -> VK10.VK_BLEND_OP_REVERSE_SUBTRACT
        BlendOp.MIN -> VK10.VK_BLEND_OP_MIN
        BlendOp.MAX -> VK10.VK_BLEND_OP_MAX
    }

private val LogicOp.vkValue
    get() = when (this) {
        LogicOp.CLEAR -> VK10.VK_LOGIC_OP_CLEAR
        LogicOp.AND -> VK10.VK_LOGIC_OP_AND
        LogicOp.AND_REVERSE -> VK10.VK_LOGIC_OP_AND_REVERSE
        LogicOp.COPY -> VK10.VK_LOGIC_OP_COPY
        LogicOp.AND_INVERTED -> VK10.VK_LOGIC_OP_AND_INVERTED
        LogicOp.NO_OP -> VK10.VK_LOGIC_OP_NO_OP
        LogicOp.XOR -> VK10.VK_LOGIC_OP_XOR
        LogicOp.OR -> VK10.VK_LOGIC_OP_OR
        LogicOp.NOR -> VK10.VK_LOGIC_OP_NOR
        LogicOp.EQUIVALENT -> VK10.VK_LOGIC_OP_EQUIVALENT
        LogicOp.INVERT -> VK10.VK_LOGIC_OP_INVERT
        LogicOp.OR_REVERSE -> VK10.VK_LOGIC_OP_OR_REVERSE
        LogicOp.COPY_INVERTED -> VK10.VK_LOGIC_OP_COPY_INVERTED
        LogicOp.OR_INVERTED -> VK10.VK_LOGIC_OP_OR_INVERTED
        LogicOp.NAND -> VK10.VK_LOGIC_OP_NAND
        LogicOp.SET -> VK10.VK_LOGIC_OP_SET
    }
